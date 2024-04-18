package eg.edu.fee.dataanalysis.stockpredict;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StockPredictionResponseModel {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonProperty(value = "opening_prediction")
    private Long openingPrediction;
    @JsonProperty(value = "closing_prediction")
    private Long closingPrediction;
}
