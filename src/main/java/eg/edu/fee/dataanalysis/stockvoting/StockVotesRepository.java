package eg.edu.fee.dataanalysis.stockvoting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockVotesRepository extends JpaRepository<StockVotes, Long> {

    @Query(value = "SELECT sv FROM StockVotes sv WHERE sv.stock.id = :id")
    Optional<StockVotes> findByStockId(Long id);

    @Query(value = """
                SELECT new eg.edu.fee.dataanalysis.stockvoting.StockVoteResponseModel(svs.name, sv.opening, sv.closing, sv.noOfVotes)
                FROM StockVotes sv
                INNER JOIN sv.stock svs
                WHERE svs.id = :id
            """)
    Optional<StockVoteResponseModel> findByStockVoteById(Long id);
}