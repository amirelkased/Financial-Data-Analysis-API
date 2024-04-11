package eg.edu.fee.dataanalysis.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    @Query("""
            SELECT t
            FROM JwtToken t
            INNER JOIN User u
            ON t.user.id = u.id
            WHERE u.id = :id AND (t.expired = FALSE OR t.revoked = FALSE)
            """)
    List<JwtToken> findAllValidTokensByUser(Long id);

    Optional<JwtToken> findByToken(String token);
}
