package hu.flowacademy.iotaspring.exchange;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ExchangeDBRepository implements ExchangeRepository {

    private final ExchangeJpaRepository exchangeJpaRepository;

    @Override
    public ExchangeData save(ExchangeData exchangeData) {
        return exchangeJpaRepository.save(exchangeData);
    }

    @Override
    public List<ExchangeData> findAll(String from, String to) {
        //        return exchangeRepository.findAll(exchangeData -> true);
        if (from != null) {
            return exchangeJpaRepository.findByFrom(from);
        } else if (to != null) {
            return exchangeJpaRepository.findByToCurrency(to);
        }
        return exchangeJpaRepository.findAll();
    }

    @Override
    public void delete(String id) {
        exchangeJpaRepository.deleteById(id);
    }

    @Override
    public Optional<ExchangeData> findOne(String id) {
        return exchangeJpaRepository.findById(id);
    }
}
