package eg.edu.fee.dataanalysis.marketindicator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseDataRepository extends JpaRepository<ResponseData, Long> {
}