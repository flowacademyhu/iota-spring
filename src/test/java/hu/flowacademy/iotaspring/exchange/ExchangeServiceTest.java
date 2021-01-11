package hu.flowacademy.iotaspring.exchange;

import hu.flowacademy.iotaspring.exchange.external.ExchangeRate;
import hu.flowacademy.iotaspring.exchange.external.ExchangeRateStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceTest {

    @Mock
    private ExchangeRepository exchangeRepository;

    @Mock
    private ExchangeRateStrategy exchangeRateStrategy;

    @InjectMocks
    private ExchangeService exchangeService;

    @Test
    public void testExchange() {
        UUID id = givenExchangeId();
        ExchangeData result = whenExchangeCreated(id);
        thenAssertExchangeResult(result, id);
    }

    private void thenAssertExchangeResult(ExchangeData result, UUID id) {
        assertEquals(id, result.getId());
        assertEquals("USD", result.getFrom());
        assertEquals("HUF", result.getTo());
        assertEquals(BigDecimal.valueOf(29500.00).setScale(2), result.getResult());
    }

    private ExchangeData whenExchangeCreated(UUID id) {
        when(exchangeRateStrategy.get("USD", "HUF")).thenReturn(
                ExchangeRate.builder()
                        .to("USD")
                        .from("HUF")
                        .rate(BigDecimal.valueOf(295))
                        .build()
        );
        var exchangeRate = ExchangeData.builder()
                .amount(BigDecimal.valueOf(100))
                .from("USD")
                .to("HUF")
                .result(BigDecimal.valueOf(29500.00).setScale(2));
        when(exchangeRepository.create(exchangeRate.build()))
                .thenReturn(exchangeRate.id(id).build());

        return exchangeService.exchange(ExchangeRequest.builder()
                .amount(BigDecimal.valueOf(100))
                .from("USD")
                .to("HUF")
                .build());
    }

    private UUID givenExchangeId() {
        return UUID.randomUUID();
    }
}