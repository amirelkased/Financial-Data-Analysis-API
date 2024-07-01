package eg.edu.fee.dataanalysis.marketindicator;

import eg.edu.fee.dataanalysis.common.CredentialMLModel;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
public class MarketIndicatorService {
    private final RestClient restClient;
    private final CredentialMLModel modelCredential;
    private final ResponseDataRepository responseDataRepository;

    public MarketIndicatorService(CredentialMLModel modelCredential, ResponseDataRepository responseDataRepository) {
        this.modelCredential = modelCredential;
        this.responseDataRepository = responseDataRepository;
        this.restClient = RestClient.create();
    }

    public ResponseData getMarkets() {
        Optional<ResponseData> responseCachedData = responseDataRepository.findById(1L);

        if (responseCachedData.isPresent()) {
            return responseCachedData.get();
        }

        ResponseData responseData = restClient.get()
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
        assert responseData != null;
        responseDataRepository.save(responseData);
        return responseData;
    }
}
