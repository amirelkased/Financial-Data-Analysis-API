package eg.edu.fee.dataanalysis.stockvoting;

import eg.edu.fee.dataanalysis.common.Stock;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_vote")
public class StockVote {

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
    @OneToMany(mappedBy = "stockVote")
    private List<UserVote> userVotes;
}
