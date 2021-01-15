package hu.flowacademy.iotaspring.customer;

import hu.flowacademy.iotaspring.exchange.ExchangeData;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

    @Id
    @Column
    private String id;

    @Column
    private String name;

//    @OneToMany(mappedBy = "customerModel")
//    private List<ExchangeData> exchangeDatas;
//    @OneToOne(fetch = FetchType.LAZY)
//    private ExchangeData exchangeData;
//    @ManyToMany(mappedBy = "customerModelList")
//    private List<ExchangeData> exchangeDatas;
}
