package hu.flowacademy.iotaspring.exchange.external;

public interface ExchangeRateStrategy {
    ExchangeRate get(String from, String to);
}
