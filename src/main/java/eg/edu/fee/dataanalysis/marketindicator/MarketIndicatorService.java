package eg.edu.fee.dataanalysis.marketindicator;

import eg.edu.fee.dataanalysis.common.CredentialMLModel;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MarketIndicatorService {
    private final RestClient restClient;
    private final CredentialMLModel modelCredential;

    public MarketIndicatorService(CredentialMLModel modelCredential) {
        this.modelCredential = modelCredential;
        this.restClient = RestClient.create();
    }

    public ResponseData getMarkets() {
        return restClient.get()
                .uri("http://127.0.0.1:5000/market-indication")
                .header("Authorization",
                        "Basic ".concat(Base64Util.encode(modelCredential
                                        .getUsername()
                                        .concat(":")
                                        .concat(modelCredential.getPassword())
                                )
                        )
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<ResponseData>() {
                })
                .getBody();
    }
}
