package eg.edu.fee.dataanalysis.stockpredict;

import eg.edu.fee.dataanalysis.common.Stock;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_prediction")
public class StockPrediction {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    @Column(name = "open_prediction")
    private Long openPrediction;
    @Column(name = "close_prediction")
    private Long closePrediction;
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
