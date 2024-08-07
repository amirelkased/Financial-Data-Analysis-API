package eg.edu.fee.dataanalysis.stockpredict;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stocks/predictions")
@RequiredArgsConstructor
public class StockPredictionRestController {
    private final StockPredictionService stockPredictionService;

    @PostMapping("")
    public ResponseEntity<List<StockPredictionResponseModel>> getStockPredicttion(
            @RequestBody StockPredictionBody stockPredictionBody) {
        List<StockPredictionResponseModel> result = stockPredictionService.getStockPrediction(stockPredictionBody);
        return ResponseEntity.ok(result);
    }
}
