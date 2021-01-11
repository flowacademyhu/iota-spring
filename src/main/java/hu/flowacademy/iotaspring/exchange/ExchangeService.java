package hu.flowacademy.iotaspring.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final ObjectMapper mapper;

    public ExchangeData exchange(ExchangeRequest exchangeRequest) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        String.format("https://api.exchangeratesapi.io/latest?base=%s&symbols=%s",
                                exchangeRequest.getTo(),
                                exchangeRequest.getFrom())
                )).build();
        try {
            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("exchange response {}", send.body());

            var exchangeResponse = buildResponse(exchangeRequest, mapper.readValue(send.body(), ExchangeApiResponse.class));

            return exchangeRepository.create(ExchangeData.builder()
                    .amount(exchangeRequest.getAmount())
                    .result(exchangeResponse.getResult())
                    .from(exchangeRequest.getFrom())
                    .to(exchangeRequest.getTo())
                    .build());
        } catch (IOException | InterruptedException e) {
            log.error("error at request {}, got: {}", exchangeRequest, e);
            throw new RuntimeException("Invalid response");
        }
    }

    private ExchangeData buildResponse(ExchangeRequest exchangeRequest, ExchangeApiResponse exchangeApiResponse) {
        BigDecimal rate = exchangeApiResponse.rates.get(exchangeRequest.getFrom());
//        return new ExchangeResponse(rate, rate.multiply(exchangeRequest.getAmount()),
//                exchangeRequest.getFrom(), exchangeRequest.getTo());
        // otherwise we should use builder for this
        return ExchangeData.builder()
                .amount(exchangeRequest.getAmount())
                .result(rate.multiply(exchangeRequest.getAmount()).setScale(2, BigDecimal.ROUND_DOWN))
                .from(exchangeRequest.getFrom())
                .to(exchangeRequest.getTo())
                .build();
    }

    public List<ExchangeData> findAll() {
        return exchangeRepository.findAll(exchangeData -> true);
    }

    public void delete(UUID id) {
        exchangeRepository.delete(id);
    }

    @Data
    @NoArgsConstructor
    static class ExchangeApiResponse {
        private Map<String, BigDecimal> rates;
        private String base;
        private String date;
    }

}
