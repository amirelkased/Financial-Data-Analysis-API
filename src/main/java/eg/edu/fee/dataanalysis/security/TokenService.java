package eg.edu.fee.dataanalysis.security;

import eg.edu.fee.dataanalysis.user.Token;
import eg.edu.fee.dataanalysis.user.TokenRepository;
import eg.edu.fee.dataanalysis.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    @Value(value = "${application.security.token.expiration}")
    private int tokenExpiration;
    @Value(value = "${application.security.token.length}")
    private int tokenLength;


    public void saveActivationToken(String generatedToken, User user) {
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(tokenExpiration))
                .user(user)
                .build();

        tokenRepository.save(token);
    }

    public String generateActivationToken() {
        final String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < tokenLength; i++) {
            codeBuilder.append(
                    characters.charAt(
                            secureRandom.nextInt(characters.length()
                            )
                    )
            );
        }

        return codeBuilder.toString();
    }
}
