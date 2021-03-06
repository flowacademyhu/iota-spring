package hu.flowacademy.iotaspring.exchange;

import hu.flowacademy.iotaspring.customer.CustomerModel;
import hu.flowacademy.iotaspring.exchange.external.ExchangeRate;
import hu.flowacademy.iotaspring.exchange.external.ExchangeRateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
//@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final ExchangeRateStrategy exchangeRateStrategy;

    public ExchangeService(@Qualifier("exchangeDBRepository") ExchangeRepository exchangeRepository,
                           ExchangeRateStrategy exchangeRateStrategy) {
        this.exchangeRepository = exchangeRepository;
        this.exchangeRateStrategy = exchangeRateStrategy;
    }

    public ExchangeData exchange(ExchangeRequest exchangeRequest) {

        ExchangeRate exchangeRate = exchangeRateStrategy.get(exchangeRequest.getFrom(), exchangeRequest.getTo());

        var exchangeResponse = buildResponse(exchangeRequest, exchangeRate);

        return exchangeRepository.save(ExchangeData.builder()
                .id(UUID.randomUUID().toString())
                .amount(exchangeRequest.getAmount())
                .result(exchangeResponse.getResult())
                .from(exchangeRequest.getFrom())
                .to(exchangeRequest.getTo())
                .customerModel(getCustomerModel(exchangeRequest))
                .build());
    }

    private CustomerModel getCustomerModel(ExchangeRequest exchangeRequest) {
        return CustomerModel.builder()
                .id(UUID.randomUUID().toString())
                .name(exchangeRequest.getCustomerName())
                .build();
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

    @Transactional(readOnly = true)
    public List<ExchangeData> findAll(String from, String to) {
        return exchangeRepository.findAll(from, to);
    }

    public void delete(String id) {
//        exchangeRepository.delete(id);
        exchangeRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public Optional<ExchangeData> findOne(String id) {
//        return exchangeRepository.findOne(id);
        return exchangeRepository.findOne(id);
    }

}
