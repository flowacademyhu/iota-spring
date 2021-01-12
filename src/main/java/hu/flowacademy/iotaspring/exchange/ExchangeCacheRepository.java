package hu.flowacademy.iotaspring.exchange;

import lombok.RequiredArgsConstructor;

import java.util.*;

//@Repository
@RequiredArgsConstructor
public class ExchangeCacheRepository implements ExchangeRepository {

    private static final int CACHE_SIZE = 10;

    // Ez reprezentalja a JPA-t, de egy altalanosabb megoldast kinal
    private final ExchangeRepository exchangeRepository;

    private Map<String, ExchangeData> cache = new HashMap<>();

    @Override
    public ExchangeData save(ExchangeData exchangeData) {
        return exchangeRepository.save(exchangeData);
    }

    public List<ExchangeData> findAll(String from, String to) {
        return exchangeRepository.findAll(from, to);
    }

    @Override
    public void delete(String id) {
//        Optional.ofNullable(cache.remove(id)).orElseThrow(() -> {
//            // TODO make custom exception
//            throw new RuntimeException("id not found " + id.toString());
//        });
        exchangeRepository.delete(id);
    }

    public Optional<ExchangeData> findOne(String id) {
//        Optional<ExchangeData> exchangeData = Optional.ofNullable(cache.get(id));
//        if (exchangeData.isEmpty()) {
//            // TODO call jpa repo
//        }
        return Optional.ofNullable(cache.get(id))
                .or(() -> exchangeRepository.findOne(id)
                        .stream()
                        .peek(this::putIntoTheCache)
                        .findFirst()
                );
    }

    private void putIntoTheCache(ExchangeData exchangeData) {
        if (cache.size() >= CACHE_SIZE) {
            cache.remove(getRandomKey());
        }
        cache.put(exchangeData.getId(), exchangeData);
    }

    private String getRandomKey() {
        return cache.keySet().toArray(new String[0])[new Random().nextInt(cache.size())];
    }
}
