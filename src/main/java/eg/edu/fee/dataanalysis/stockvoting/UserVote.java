package eg.edu.fee.dataanalysis.stockvoting;

import eg.edu.fee.dataanalysis.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_vote")
public class UserVote {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "vote_id")
    private StockVote stockVote;
}
