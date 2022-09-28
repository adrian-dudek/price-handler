package pl.sii.price.domain.ports;

import java.util.Optional;

import pl.sii.price.domain.entity.PriceFeed;

public interface PriceFeedRepository {

    void persist(PriceFeed priceFeed);
    
    Optional<PriceFeed> find(String instrumentName);
}
