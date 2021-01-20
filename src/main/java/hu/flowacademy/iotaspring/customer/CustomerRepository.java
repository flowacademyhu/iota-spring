package hu.flowacademy.iotaspring.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, String> {
    Optional<UserDetails> findByUsername(String username);
}
