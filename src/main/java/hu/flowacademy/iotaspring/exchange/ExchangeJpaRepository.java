package hu.flowacademy.iotaspring.exchange;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeJpaRepository extends JpaRepository<ExchangeData, String> {

    // SELECT * FROM exchange_rate WHERE from_currency=?
    List<ExchangeData> findByFrom(String from);

    @Query("SELECT e FROM ExchangeData e WHERE e.to=?1")
    List<ExchangeData> findByToCurrency(String to);
    // ez lehetne igy is: List<ExchangeData> findByTo(String to);
}
