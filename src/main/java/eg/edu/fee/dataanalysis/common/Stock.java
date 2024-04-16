package eg.edu.fee.dataanalysis.common;

import eg.edu.fee.dataanalysis.stockpredict.StockPrediction;
import eg.edu.fee.dataanalysis.stockvoting.StockVotes;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToOne(mappedBy = "stock", cascade = CascadeType.ALL)
    private StockVotes stockVotes;
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockPrediction> stockPredictions;
}
