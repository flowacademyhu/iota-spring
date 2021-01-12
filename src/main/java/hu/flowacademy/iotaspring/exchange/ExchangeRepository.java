package hu.flowacademy.iotaspring.exchange;

import java.util.List;
import java.util.Optional;

public interface ExchangeRepository {

    ExchangeData save(ExchangeData exchangeData);

    List<ExchangeData> findAll(String from, String to);

    void delete(String id);

    Optional<ExchangeData> findOne(String id);

}
