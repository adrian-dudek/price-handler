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
    public void persistIfIsTheLatest(PriceFeed priceFeed) {        
        priceFeedMap.compute(
                priceFeed.getInstrumentName(),
                (existedInstrumentName, existedPriceFeed) -> getTheLatest(existedPriceFeed, priceFeed));      
    }

    @Override
    public Optional<PriceFeed> find(String instrumentName) {
        return Optional.ofNullable(priceFeedMap.get(instrumentName));
    }
    
    private PriceFeed getTheLatest(PriceFeed existedPriceFeed, PriceFeed newPriceFeed) {
        return existedPriceFeed == null || newPriceFeed.getTimestamp().isAfter(existedPriceFeed.getTimestamp())
                ? newPriceFeed 
                : existedPriceFeed;
    }
}
