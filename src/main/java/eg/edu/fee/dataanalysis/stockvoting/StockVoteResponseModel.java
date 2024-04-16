package eg.edu.fee.dataanalysis.stockvoting;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockVoteResponseModel {
    private String name;
    private Long open;
    private Long close;
    private Long votes;
}
