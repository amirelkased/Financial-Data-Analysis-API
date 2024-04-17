package eg.edu.fee.dataanalysis.stockvoting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockVoteRepository extends JpaRepository<StockVote, Long> {

    @Query(value = "SELECT sv FROM StockVote sv WHERE sv.stock.id = :id")
    Optional<StockVote> findByStockId(Long id);

    @Query(value = """
                SELECT new eg.edu.fee.dataanalysis.stockvoting.StockVoteResponseModel(svs.name, sv.opening, sv.closing, sv.noOfVotes)
                FROM StockVote sv
                INNER JOIN sv.stock svs
                WHERE svs.id = :id
            """)
    Optional<StockVoteResponseModel> findByStockVoteById(Long id);
}