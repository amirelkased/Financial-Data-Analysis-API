package eg.edu.fee.dataanalysis.stockpredict;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockPredictionBody {
    private Long stockId;
    private Integer noOfDay;
}
