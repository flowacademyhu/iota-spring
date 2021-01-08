package hu.flowacademy.iotaspring.exchange;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeData {
    private UUID id;
    @NonNull
    private String from; // TODO we should use enum here
    @NonNull
    private String to;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private BigDecimal result;
}
