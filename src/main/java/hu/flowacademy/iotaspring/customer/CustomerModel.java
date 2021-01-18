package hu.flowacademy.iotaspring.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Version
    @Column
    private int version;

//    @OneToMany(mappedBy = "customerModel")
//    private List<ExchangeData> exchangeDatas;
//    @OneToOne(fetch = FetchType.LAZY)
//    private ExchangeData exchangeData;
//    @ManyToMany(mappedBy = "customerModelList")
//    private List<ExchangeData> exchangeDatas;
}
