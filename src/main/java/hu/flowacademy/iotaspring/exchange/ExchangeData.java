package hu.flowacademy.iotaspring.exchange;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

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
