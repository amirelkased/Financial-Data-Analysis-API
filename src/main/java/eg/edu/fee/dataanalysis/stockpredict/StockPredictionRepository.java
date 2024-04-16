package eg.edu.fee.dataanalysis.stockpredict;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPredictionRepository extends JpaRepository<StockPrediction, Long> {
}