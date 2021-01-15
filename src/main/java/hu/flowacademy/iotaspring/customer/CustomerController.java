package hu.flowacademy.iotaspring.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer")
    public List<CustomerModel> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/customer/{id}")
    public Optional<CustomerModel> findOne(@PathVariable String id) {
        return customerService.findOne(UUID.fromString(id));
    }

    @PostMapping("/customer")
    public CustomerModel save(@RequestBody CustomerModel customerModel) {
        return customerService.save(customerModel);
    }

    @PutMapping("/customer")
    public CustomerModel update(@RequestBody CustomerModel customerModel) {
        return customerService.save(customerModel);
    }

    @DeleteMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        customerService.delete(UUID.fromString(id));
    }

}
