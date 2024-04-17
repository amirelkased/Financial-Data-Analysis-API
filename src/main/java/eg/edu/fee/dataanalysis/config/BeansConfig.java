package eg.edu.fee.dataanalysis.config;


import eg.edu.fee.dataanalysis.common.Stock;
import eg.edu.fee.dataanalysis.common.StockRepository;
import eg.edu.fee.dataanalysis.role.Role;
import eg.edu.fee.dataanalysis.role.RoleRepository;
import eg.edu.fee.dataanalysis.stockvoting.StockVote;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class BeansConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CommandLineRunner commandLineRunner(RoleRepository roleRepository,
                                               StockRepository stockRepository) {
        return e -> {
            Role role = Role.builder()
                    .name("USER")
                    .createdDate(LocalDateTime.now())
                    .build();

            roleRepository.save(role);

            Stock stock = Stock.builder()
                    .name("Apple")
                    .build();
            StockVote stockVote = StockVote.builder()
                    .opening(10L)
                    .closing(102L)
                    .noOfVotes(2443L)
                    .stock(stock)
                    .build();
            stock.setStockVote(stockVote);
            stockRepository.save(stock);
        };
    }
}
