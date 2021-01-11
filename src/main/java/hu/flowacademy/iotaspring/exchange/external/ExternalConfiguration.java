package hu.flowacademy.iotaspring.exchange.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.flowacademy.iotaspring.config.GlobalConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GlobalConfiguration.class)
public class ExternalConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public ExchangeRateStrategy exchangeRateStrategy() {
        return new DefaultHttpClient(objectMapper);
    }

}
