package hu.flowacademy.iotaspring.config;

import hu.flowacademy.iotaspring.exchange.ExchangeData;
import hu.flowacademy.iotaspring.exchange.ExchangeJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InitDataLoader {

    private final ExchangeJpaRepository exchangeJpaRepository;

    @PostConstruct
    public void init() {
        log.info("Running init data loader...");
//        IntStream.range(0, 5).forEach(value -> {
//            System.out.println(faker.address().city());
//        });
        generateExchangeData();
    }

    private void generateExchangeData() {
//        // ugyanaz mint az IntStream.range(0, 10)
//        for (int i = 0; i < 10; i++) {
//
//        }
        IntStream.range(0, 10).forEach(value -> {
            int amount = new Random().nextInt(1000);
            exchangeJpaRepository.save(ExchangeData.builder()
                    .id(UUID.randomUUID().toString())
                    .from("USD")
                    .to("HUF")
                    .amount(BigDecimal.valueOf(amount))
                    .result(BigDecimal.valueOf(Math.random() * 400))
                    .build());
        });
    }

}
