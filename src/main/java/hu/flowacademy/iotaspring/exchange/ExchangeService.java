package hu.flowacademy.iotaspring.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
@Service
public class ExchangeService {

    public ExchangeResponse exchange(ExchangeRequest exchangeRequest) {
        ObjectMapper mapper = new ObjectMapper();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        String.format("https://api.exchangeratesapi.io/latest?base=%s&symbols=%s",
                                exchangeRequest.getFrom(),
                                exchangeRequest.getTo())
                )).build();
        try {
            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("exchange response {}", send.body());

            return buildResponse(exchangeRequest, mapper.readValue(send.body(), ExchangeApiResponse.class));
        } catch (IOException | InterruptedException e) {
            log.error("error at request {}, got: {}", exchangeRequest, e);
            throw new RuntimeException("Invalid response");
        }
    }

    private ExchangeResponse buildResponse(ExchangeRequest exchangeRequest, ExchangeApiResponse exchangeApiResponse) {
        BigDecimal rate = exchangeApiResponse.rates.get(exchangeRequest.getTo());
//        return new ExchangeResponse(rate, rate.multiply(exchangeRequest.getAmount()),
//                exchangeRequest.getFrom(), exchangeRequest.getTo());
        // otherwise we should use builder for this
        return ExchangeResponse.builder()
                .exchangeRate(rate)
                .result(rate.multiply(exchangeRequest.getAmount()))
                .from(exchangeRequest.getFrom())
                .to(exchangeRequest.getTo())
                .build();
    }

    @Data
    @NoArgsConstructor
    static class ExchangeApiResponse {
        private Map<String, BigDecimal> rates;
        private String base;
        private String date;
    }

}
