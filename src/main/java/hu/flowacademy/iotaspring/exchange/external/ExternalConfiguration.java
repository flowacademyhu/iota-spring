package hu.flowacademy.iotaspring.exchange.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.flowacademy.iotaspring.config.GlobalConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;

@Configuration
@Import(GlobalConfiguration.class)
@RequiredArgsConstructor
public class ExternalConfiguration {

    private final ObjectMapper objectMapper;

    @Value("${external.base-url-pattern}")
    private String externalUrlPattern;

    @Value("${external.client}")
    private String clientType;

    @Bean
    public ExchangeRateStrategy exchangeRateStrategy() {
        if ("rest-template".equals(clientType)) {
            return new RestTemplateClient(externalUrlPattern, restTemplate());
        }
        return new DefaultHttpClient(externalUrlPattern, objectMapper, httpClient());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

}
