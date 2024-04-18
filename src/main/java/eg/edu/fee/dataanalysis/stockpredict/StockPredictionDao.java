package eg.edu.fee.dataanalysis.stockpredict;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor

public class StockPredictionDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    @Async
    public void truncateUserVote() {
        entityManager.createNativeQuery("""
                TRUNCATE TABLE stock_prediction
                """).executeUpdate();
    }
}


