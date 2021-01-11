package hu.flowacademy.iotaspring.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceTest {

    @Mock
    private ExchangeRepository exchangeRepository;

    @Spy
    private ObjectMapper objectMapper;

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
    }

    private ExchangeData whenExchangeCreated(UUID id) {
        when(exchangeRepository.create(any()))
                .thenReturn(ExchangeData.builder()
                        .id(id)
                        .amount(BigDecimal.valueOf(100))
                        .from("USD")
                        .to("HUF")
                        .result(BigDecimal.valueOf(29400))
                        .build());

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