package eg.edu.fee.dataanalysis.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "model.credential")
@RequiredArgsConstructor
@Data
public class CredentialMLModel {
    private String username;
    private String password;
}
