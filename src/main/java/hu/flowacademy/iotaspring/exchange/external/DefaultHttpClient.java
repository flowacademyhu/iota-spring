package hu.flowacademy.iotaspring.exchange.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@RequiredArgsConstructor
public class DefaultHttpClient implements ExchangeRateStrategy {

//    @Value("${external.params}")
//    private List<String> EXCHANGE_API_PARAMS;

    private final String EXCHANGE_API_PATTERN;
    private final ObjectMapper mapper;
    private final HttpClient client;

    @Override
    public ExchangeRate get(String from, String to) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        String.format(EXCHANGE_API_PATTERN, from, to)
                )).build();
        try {
            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("exchange response {}", send.body());

            ExchangeApiResponse response = mapper.readValue(send.body(), ExchangeApiResponse.class);

            return ExchangeRate.builder()
                    .from(from)
                    .to(to)
                    .rate(response.getRates().get(to))
                    .build();
        } catch (IOException | InterruptedException e) {
            log.error("error at request {}, {}, got: {}", from, to, e);
            throw new RuntimeException("Invalid response");
        }
    }
}
