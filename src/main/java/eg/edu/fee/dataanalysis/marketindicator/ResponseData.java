package eg.edu.fee.dataanalysis.marketindicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class ResponseData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<RankItem> profits;
    @OneToMany(cascade = CascadeType.ALL)
    private List<RankItem> losses;
}
