package eg.edu.fee.dataanalysis.marketindicator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class RankItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String name;
    @Column(name = "_rank")
    private Long rank;
}
