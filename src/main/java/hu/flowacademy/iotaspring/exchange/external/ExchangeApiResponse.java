package hu.flowacademy.iotaspring.exchange.external;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
class ExchangeApiResponse {
    private Map<String, BigDecimal> rates;
    private String base;
    private String date;
}
