package hu.flowacademy.iotaspring.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public List<CustomerModel> findAll() {
        return customerRepository.findAll();
    }

    public Optional<CustomerModel> findOne(UUID id) {
        return customerRepository.findById(id.toString());
    }

    public CustomerModel save(CustomerModel customerModel) {
        if (customerModel.getId() == null) {
            customerModel.setId(UUID.randomUUID().toString());
            customerModel.setPassword(
                    passwordEncoder.encode(
                            customerModel.getPassword()
                    )
            );
        }
        try {
            return customerRepository.save(customerModel);
        } catch (OptimisticLockException e) {
            // FIXME dead code
            return customerRepository.findById(customerModel.getId()).orElseThrow();
        }
    }

    public void delete(UUID id) {
        customerRepository.deleteById(id.toString());
    }
}
