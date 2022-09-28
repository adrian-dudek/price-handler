package pl.sii.price.domain.ports;

import pl.sii.price.domain.entity.PriceFeed;

public interface PriceFeedService {

    void add(PriceFeed priceFeed);
    
    PriceFeed get(String instrumentName);
}
