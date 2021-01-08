package hu.flowacademy.iotaspring.exchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exchange REST API
 * <p>
 * Functionalities:
 * - customer can send a two currencies and an amount, we return with the exchanged amount
 * - we'll store the did exchanges (in memory, in a map)
 * - CRUD (Create-Read-Update-Delete) - we'll skip update
 */
@Slf4j
@RestController
// add an /api prefix before our mapping paths
// for example: GET /api/exchange
@RequestMapping("/api")
@RequiredArgsConstructor // this also generate the constructor for required fields
public class ExchangeController {

    private final ExchangeService exchangeService;

//    // this is same as the @RequiredArgsConstructor
//    public ExchangeController(ExchangeService exchangeService) {
//        this.exchangeService = exchangeService;
//    }

    @PostMapping("/exchange")
    public ExchangeResponse exchange(@RequestBody ExchangeRequest request) {
        return exchangeService.exchange(request);
    }

}
