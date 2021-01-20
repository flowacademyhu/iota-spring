package hu.flowacademy.iotaspring.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel implements UserDetails {

    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Version
    @Column
    private int version;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    @OneToMany(mappedBy = "customerModel")
//    private List<ExchangeData> exchangeDatas;
//    @OneToOne(fetch = FetchType.LAZY)
//    private ExchangeData exchangeData;
//    @ManyToMany(mappedBy = "customerModelList")
//    private List<ExchangeData> exchangeDatas;
}

enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}