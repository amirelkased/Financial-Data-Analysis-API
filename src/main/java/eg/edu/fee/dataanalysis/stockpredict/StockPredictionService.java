package eg.edu.fee.dataanalysis.stockpredict;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockPredictionService {
    private final RestClient restClient;
    private final ModelCredential modelCredential;
    private final StockPredictionRepository stockPredictionRepository;

    {
        this.restClient = RestClient.create();
    }

    public List<StockPredictionResponseModel> getStockPrediction(StockPredictionBody stock) {

        List<StockPredictionResponseModel> responseModelList = getStockIfExists(stock.getStockId());

        if (!responseModelList.isEmpty()) {
            return responseModelList;
        }

        responseModelList =
                Collections.singletonList(
                        restClient.post()
                                .uri("http://127.0.0.1:5000/predict")
                                .header("Authorization",
                                        Base64Util.encode(
                                                modelCredential.getUsername().concat(modelCredential.getPassword())
                                        )
                                )
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(
                                        StockPredictionBody.builder()
                                                .stockId(stock.getStockId())
                                                .noOfDay(stock.getNoOfDay())
                                                .build()
                                ).retrieve()
                                .body(StockPredictionResponseModel.class)
                );

        return responseModelList;
    }

    private List<StockPredictionResponseModel> getStockIfExists(Long stockId) {
        return stockPredictionRepository.findStockPrediction(stockId);
    }
}
