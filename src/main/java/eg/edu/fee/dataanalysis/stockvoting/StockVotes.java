package eg.edu.fee.dataanalysis.stockvoting;

import eg.edu.fee.dataanalysis.common.Stock;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_votes")
public class StockVotes {

    @Id
    @GeneratedValue
    private Long id;
    private Long opening;
    private Long closing;
    @Column(name = "votes")
    private Long noOfVotes;
    @OneToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
