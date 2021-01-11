package hu.flowacademy.iotaspring.exchange.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class RestTemplateClient implements ExchangeRateStrategy {

    private final String externalApiUrl;
    private final RestTemplate restTemplate;

    @Override
    public ExchangeRate get(String from, String to) {
        return Optional.ofNullable(
                restTemplate.getForObject(String.format(externalApiUrl, from, to), ExchangeApiResponse.class)
        ).map(response ->
                ExchangeRate.builder()
                        .from(from)
                        .to(to)
                        .rate(response.getRates().get(to))
                        .build()
        ).orElseThrow(() -> new RuntimeException("invalid http response"));
    }
}
