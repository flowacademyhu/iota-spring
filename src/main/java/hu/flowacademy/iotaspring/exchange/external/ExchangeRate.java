package hu.flowacademy.iotaspring.exchange.external;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    private String from;
    private String to;
    private BigDecimal rate;
}
