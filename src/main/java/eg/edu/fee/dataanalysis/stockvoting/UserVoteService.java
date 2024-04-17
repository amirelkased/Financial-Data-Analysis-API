package eg.edu.fee.dataanalysis.stockvoting;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserVoteService {

    @PersistenceContext
    private final EntityManager entityManager;

    @Scheduled(cron = "0 5 16 * * *")
    @Transactional
    @Async
    public void truncateUserVote() {
        entityManager.createNativeQuery("""
                TRUNCATE TABLE user_vote
                """).executeUpdate();
    }
}
