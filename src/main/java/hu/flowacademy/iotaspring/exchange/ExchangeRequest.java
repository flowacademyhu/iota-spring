package hu.flowacademy.iotaspring.exchange;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRequest {

    @NonNull
    private String from; // TODO we should use enum here
    @NonNull
    private String to;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private String customerName;

}
