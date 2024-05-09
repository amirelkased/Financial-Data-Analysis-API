package eg.edu.fee.dataanalysis.marketindicator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private List<RankItem> profits;
    private List<RankItem> losses;

}
