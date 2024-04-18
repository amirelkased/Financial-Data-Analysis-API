package eg.edu.fee.dataanalysis.stockpredict;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Builder
public class StockPredictionBody {
    private long stockId;
    private int noOfDay;
}
