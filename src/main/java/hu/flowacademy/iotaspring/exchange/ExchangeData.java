package hu.flowacademy.iotaspring.exchange;

import hu.flowacademy.iotaspring.customer.CustomerModel;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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

    @ManyToOne(cascade = CascadeType.ALL)
    private CustomerModel customerModel;

//    @ManyToMany
//    @JoinTable(
//            name = "exchange_customer_ids",
//            joinColumns = @JoinColumn(name = "exchange_data_id"),
//            inverseJoinColumns = @JoinColumn(name = "customer_model_id")
//    )
//    private List<CustomerModel> customerModelList;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "exchange_customer_id")
//    private CustomerModel customerModel;
}
