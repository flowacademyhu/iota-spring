package hu.flowacademy.iotaspring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class GlobalConfiguration {

    @Bean
    @Scope("singleton")
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
