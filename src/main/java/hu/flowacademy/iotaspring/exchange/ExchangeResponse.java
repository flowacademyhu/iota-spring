package hu.flowacademy.iotaspring.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResponse {
    private BigDecimal exchangeRate;
    private BigDecimal result;
    private String from;
    private String to;
}
