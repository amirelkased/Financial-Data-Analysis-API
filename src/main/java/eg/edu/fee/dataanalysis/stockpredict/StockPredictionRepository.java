package eg.edu.fee.dataanalysis.stockpredict;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPredictionRepository extends JpaRepository<StockPrediction, Long> {

    @Query("""
                SELECT new eg.edu.fee.dataanalysis.stockpredict.
                StockPredictionResponseModel(sp.date,sp.openPrediction,sp.closePrediction)
                FROM StockPrediction sp
                INNER JOIN sp.stock sps
                WHERE sps.id = :stockId
            """)
    List<StockPredictionResponseModel> findStockPrediction(Long stockId);
}