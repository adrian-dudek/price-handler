package pl.sii.price.adapters.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import pl.sii.price.domain.entity.PriceFeed;
import pl.sii.price.domain.ports.PriceFeedRepository;

public class InMemoryPriceFeedRepository implements PriceFeedRepository{

    private final Map<String, PriceFeed> priceFeedMap;
    
    public InMemoryPriceFeedRepository() {
        priceFeedMap = new ConcurrentHashMap<>();
    }
    
    @Override
    public void persist(PriceFeed priceFeed) {
        priceFeedMap.put(priceFeed.getInstrumentName(), priceFeed);       
    }

    @Override
    public Optional<PriceFeed> find(String instrumentName) {
        PriceFeed priceFeed = priceFeedMap.get(instrumentName);
        if(priceFeed != null) {
            return Optional.of(priceFeed);
        }
        else {
            return Optional.empty();
        }
    }
}
