package pl.sii.price.adapters.api;

import pl.sii.price.domain.ports.PriceFeedService;

public class PriceFeedEndpoint {
    
    private final PriceFeedService priceFeedService;
    
    public PriceFeedEndpoint (PriceFeedService priceFeedService) {
        this.priceFeedService = priceFeedService;
    }
    
    public PriceFeedResponse getTheLatestPriceFeed(String instrumentName) {
        return PriceFeedResponse.of(priceFeedService.get(instrumentName));
    }
}
