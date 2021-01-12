package hu.flowacademy.iotaspring.exchange;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

// Adatabazis annotaciok
@Entity
@Table
// Lombok annotaciok
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeData {
    @Id
    @Column
    private String id;

    @Column(name = "fromCurrency")
    @NonNull
    private String from; // TODO we should use enum here

    @Column(name = "toCurrency")
    @NonNull
    private String to;

    @Column
    @NonNull
    private BigDecimal amount;

    // @Transient // ha nem akarjuk, hogy a field letarolva legyen a db-ben
    @Column
    @NonNull
    private BigDecimal result;
}
