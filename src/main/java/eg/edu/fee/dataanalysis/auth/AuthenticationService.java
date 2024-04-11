package eg.edu.fee.dataanalysis.auth;

import eg.edu.fee.dataanalysis.email.EmailService;
import eg.edu.fee.dataanalysis.email.EmailTemplateName;
import eg.edu.fee.dataanalysis.role.Role;
import eg.edu.fee.dataanalysis.role.RoleRepository;
import eg.edu.fee.dataanalysis.security.JwtService;
import eg.edu.fee.dataanalysis.security.JwtToken;
import eg.edu.fee.dataanalysis.security.JwtTokenRepository;
import eg.edu.fee.dataanalysis.user.Token;
import eg.edu.fee.dataanalysis.user.TokenRepository;
import eg.edu.fee.dataanalysis.user.User;
import eg.edu.fee.dataanalysis.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtTokenRepository jwtTokenRepository;
    @Value(value = "${application.mailing.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest registrationRequest) throws MessagingException {

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));

        User user = User
                .builder()
                .firstname(registrationRequest.firstname())
                .lastname(registrationRequest.lastname())
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .roles(List.of(userRole))
                .accountLocked(false)
                .enabled(false)
                .build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        String newToken = generateAndSaveActivationToken(user);
        // send mail
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATION_ACCOUNT,
                activationUrl,
                newToken,
                "Activation Account"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationToken(6);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build();

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationToken(int length) {
        final String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            codeBuilder.append(
                    characters.charAt(
                            secureRandom.nextInt(characters.length()
                            )
                    )
            );
        }

        return codeBuilder.toString();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        HashMap<String, Object> claims = new HashMap<>();
        User user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());
        String generatedJwtToken = jwtService.generateToken(claims, user);

        // Save user along jwt token
        revokeAllUserToken(user.getId());
        saveJwtToken(generatedJwtToken, user);

        return AuthenticationResponse.builder()
                .token(generatedJwtToken)
                .build();
    }

    private void saveJwtToken(String generatedJwtToken, User user) {
        JwtToken jwtToken = JwtToken.builder()
                .token(generatedJwtToken)
                .user(user)
                .revoked(false)
                .expired(false)
                .build();
        jwtTokenRepository.save(jwtToken);
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token is not exists"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent.");
        }

        User user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    @Transactional
    public void revokeJwtToken(String token) {
        token = token.substring(7);
        JwtToken savedToken = jwtTokenRepository.findByToken(token).orElseThrow(
                () -> new RuntimeException("Token not found")
        );

        savedToken.setRevoked(true);
        jwtTokenRepository.save(savedToken);
    }

    @Transactional
    public void revokeAllUserToken(Long userId) {
        List<JwtToken> allUserToken = jwtTokenRepository.findAllValidTokensByUser(userId);

        if (allUserToken.isEmpty()) {
            return;
        }

        allUserToken.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        jwtTokenRepository.saveAll(allUserToken);
    }
}
