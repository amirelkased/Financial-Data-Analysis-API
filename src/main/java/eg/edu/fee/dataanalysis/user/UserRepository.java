package eg.edu.fee.dataanalysis.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("""
                SELECT u,sv, s
                FROM User u
                INNER JOIN UserVote uv ON u.id = uv.user.id
                INNER JOIN StockVote sv ON uv.stockVote.id = sv.id
                INNER JOIN Stock s ON s.id = sv.stock.id
            """)
    List<Object[]> findUsersAndTheirStockVotes();
}
