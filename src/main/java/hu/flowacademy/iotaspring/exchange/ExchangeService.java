package hu.flowacademy.iotaspring.exchange;

import hu.flowacademy.iotaspring.exchange.external.ExchangeRate;
import hu.flowacademy.iotaspring.exchange.external.ExchangeRateStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final ExchangeRateStrategy exchangeRateStrategy;
    private final ExchangeJpaRepository exchangeJpaRepository;

    public ExchangeData exchange(ExchangeRequest exchangeRequest) {

        ExchangeRate exchangeRate = exchangeRateStrategy.get(exchangeRequest.getFrom(), exchangeRequest.getTo());

        var exchangeResponse = buildResponse(exchangeRequest, exchangeRate);

        return exchangeJpaRepository.save(ExchangeData.builder()
                .id(UUID.randomUUID().toString())
                .amount(exchangeRequest.getAmount())
                .result(exchangeResponse.getResult())
                .from(exchangeRequest.getFrom())
                .to(exchangeRequest.getTo())
                .build());
    }

    ExchangeData buildResponse(ExchangeRequest exchangeRequest, ExchangeRate exchangeRate) {
//        return new ExchangeResponse(rate, rate.multiply(exchangeRequest.getAmount()),
//                exchangeRequest.getFrom(), exchangeRequest.getTo());
        // otherwise we should use builder for this
        return ExchangeData.builder()
                .amount(exchangeRequest.getAmount())
                .result(exchangeRate.getRate()
                        .multiply(exchangeRequest.getAmount())
                        .setScale(2, BigDecimal.ROUND_DOWN))
                .from(exchangeRequest.getFrom())
                .to(exchangeRequest.getTo())
                .build();
    }

    public List<ExchangeData> findAll(String from, String to) {
//        return exchangeRepository.findAll(exchangeData -> true);
        if (from != null) {
            return exchangeJpaRepository.findByFrom(from);
        } else if (to != null) {
            return exchangeJpaRepository.findByToCurrency(to);
        }
        return exchangeJpaRepository.findAll();
    }

    public void delete(String id) {
//        exchangeRepository.delete(id);
        exchangeJpaRepository.deleteById(id);
    }

    public Optional<ExchangeData> findOne(String id) {
//        return exchangeRepository.findOne(id);
        return exchangeJpaRepository.findById(id);
    }

}
