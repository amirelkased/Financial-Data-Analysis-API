package eg.edu.fee.dataanalysis.stockpredict;

import eg.edu.fee.dataanalysis.common.CredentialMLModel;
import eg.edu.fee.dataanalysis.common.Stock;
import eg.edu.fee.dataanalysis.common.StockRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockPredictionService {
    private final RestClient restClient;
    private final CredentialMLModel modelCredential;
    private final StockPredictionRepository stockPredictionRepository;
    private final StockRepository stockRepository;

    public StockPredictionService(CredentialMLModel modelCredential, StockPredictionRepository stockPredictionRepository, StockRepository stockRepository) {
        this.stockRepository = stockRepository;
        this.restClient = RestClient.create();
        this.modelCredential = modelCredential;
        this.stockPredictionRepository = stockPredictionRepository;
    }

    @Transactional
    public List<StockPredictionResponseModel> getStockPrediction(StockPredictionBody stock) {

        List<StockPredictionResponseModel> responseModelList = getStockIfExists(stock.getStockId());

        if (!responseModelList.isEmpty()) {
            return responseModelList;
        }

        responseModelList = restClient.post()
                .uri("http://127.0.0.1:5000/predict")
                .header("Authorization",
                        "Basic ".concat(Base64Util.encode(modelCredential
                                        .getUsername()
                                        .concat(":")
                                        .concat(modelCredential.getPassword())
                                )
                        )
                )
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        StockPredictionBody.builder()
                                .stockId(stock.getStockId())
                                .noOfDay(stock.getNoOfDay())
                                .build()
                ).retrieve()
                .toEntity(new ParameterizedTypeReference<List<StockPredictionResponseModel>>() {
                }).getBody();

        assert responseModelList != null;
        saveStockPrediction(responseModelList, stock.getStockId());
        return responseModelList;
    }

    private List<StockPredictionResponseModel> getStockIfExists(Long stockId) {
        return stockPredictionRepository.findStockPrediction(stockId);
    }

    @Transactional
    protected void saveStockPrediction(List<StockPredictionResponseModel> responseModels, Long stockId) {

        Stock stock = stockRepository.findById(stockId).orElseThrow(
                () -> new RuntimeException("Stock %d not found".formatted(stockId))
        );
        List<StockPrediction> stockPredictionList = new ArrayList<>();
        responseModels.forEach(e -> {
            StockPrediction stockPrediction = StockPrediction.builder()
                    .date(e.getDate())
                    .openPrediction(e.getOpeningPrediction())
                    .closePrediction(e.getClosingPrediction())
                    .stock(stock)
                    .build();

            stockPredictionList.add(stockPrediction);
        });
        persistStockPrediction(stockPredictionList);
    }

    @Transactional
    protected void persistStockPrediction(List<StockPrediction> stockPredictionList) {
        stockPredictionRepository.saveAll(stockPredictionList);
    }

    public void presistDummyData(List<StockPredictionResponseModel> data) {
        saveStockPrediction(data, 1L);
    }
}
