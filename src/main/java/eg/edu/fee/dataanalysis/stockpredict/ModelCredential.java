package eg.edu.fee.dataanalysis.stockpredict;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "model.credential")
@RequiredArgsConstructor
@Data
public class ModelCredential {
    private String username;
    private String password;
}
