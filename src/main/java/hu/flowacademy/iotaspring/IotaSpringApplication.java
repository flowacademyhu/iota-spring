package hu.flowacademy.iotaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class IotaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotaSpringApplication.class, args);
    }

}
