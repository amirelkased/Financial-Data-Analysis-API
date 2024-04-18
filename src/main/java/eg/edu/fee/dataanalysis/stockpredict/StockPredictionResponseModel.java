package eg.edu.fee.dataanalysis.stockpredict;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StockPredictionResponseModel {
    private LocalDate date;
    private Long openingPrediction;
    private Long closingPrediction;
}
