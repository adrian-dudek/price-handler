package pl.sii.price.domain.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import pl.sii.price.domain.ports.PriceFeedRepository;
import pl.sii.price.domain.ports.PriceFeedService;

public class PriceFeedCommissionService implements PriceFeedService {

    private final int scaleCalculation;
    
    private final RoundingMode roundingMode;
    
    private final BigDecimal buyCommissionIndicator;
    
    private final BigDecimal sellCommissionIndicator;
    
    private final PriceFeedRepository priceFeedRepository;
    
    public PriceFeedCommissionService(PriceFeedRepository priceFeedRepository, double commisionValue,
            int scaleCalculation, RoundingMode roundingMode) {
        this.priceFeedRepository = priceFeedRepository;
        this.scaleCalculation = scaleCalculation;
        this.roundingMode = roundingMode;
        this.buyCommissionIndicator = BigDecimal.valueOf(1.0 - commisionValue);        
        this.sellCommissionIndicator = BigDecimal.valueOf(1.0 + commisionValue);
    }
    
    @Override
    public void add(PriceFeed priceFeed) {        
        priceFeed.setBuyPrice(priceFeed.getBuyPrice()
                .multiply(buyCommissionIndicator)
                .setScale(scaleCalculation, roundingMode));
        priceFeed.setSellPrice(priceFeed.getSellPrice()
                .multiply(sellCommissionIndicator)
                .setScale(scaleCalculation, roundingMode));
        
        priceFeedRepository.find(priceFeed.getInstrumentName()).ifPresentOrElse(
                existedPriceFeed -> persistIfTheLatest(existedPriceFeed, priceFeed),
                () -> priceFeedRepository.persist(priceFeed));
    }

    @Override
    public PriceFeed get(String instrumentName) {
        return priceFeedRepository.find(instrumentName).orElseThrow(() -> new PriceFeedNotExists(
                String.format("Can't find price feed for instrumentName: %s", instrumentName)));
    }
    
    private void persistIfTheLatest(PriceFeed existedPriceFeed, PriceFeed newPriceFeed) {
        if(newPriceFeed.getTimestamp().isAfter(existedPriceFeed.getTimestamp())) {
            priceFeedRepository.persist(newPriceFeed);
        }
    }
}
