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

    public static final String EXCHANGE_API_PATTERN = "https://api.exchangeratesapi.io/latest?base=%s&symbols=%s";

    private final ObjectMapper mapper;

    @Override
    public ExchangeRate get(String from, String to) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        String.format(EXCHANGE_API_PATTERN, to, from)
                )).build();
        try {
            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("exchange response {}", send.body());

            ExchangeApiResponse response = mapper.readValue(send.body(), ExchangeApiResponse.class);

            return ExchangeRate.builder()
                    .from(from)
                    .to(to)
                    .rate(response.getRates().get(from))
                    .build();
        } catch (IOException | InterruptedException e) {
            log.error("error at request {}, {}, got: {}", from, to, e);
            throw new RuntimeException("Invalid response");
        }
    }
}
