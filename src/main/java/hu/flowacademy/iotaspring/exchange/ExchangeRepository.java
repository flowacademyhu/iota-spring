package hu.flowacademy.iotaspring.exchange;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class ExchangeRepository {

    private Map<UUID, ExchangeData> data = new HashMap<>();

    public ExchangeData create(ExchangeData exchangeData) {
        if (exchangeData.getId() != null) {
            throw new IllegalArgumentException();
        }
        exchangeData.setId(UUID.randomUUID());
        data.put(exchangeData.getId(), exchangeData);
        return exchangeData;
    }

    public List<ExchangeData> findAll(Predicate<ExchangeData> p) {
        return data.values().stream().filter(p).collect(Collectors.toList());
    }

    public void delete(UUID id) {
        if (data.remove(id) == null) {
            throw new RuntimeException("id not found " + id.toString());
        }
    }

}
