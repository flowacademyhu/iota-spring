package hu.flowacademy.iotaspring.exchange;

import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeControllerTest {

    @BeforeAll
    public void before() {
        ExchangeController exchangeController = new ExchangeController(new ExchangeService());
    }

}