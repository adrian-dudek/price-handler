package pl.sii.price.config;

import java.math.RoundingMode;

import lombok.Getter;
import pl.sii.price.adapters.api.PriceFeedEndpoint;
import pl.sii.price.adapters.messagebroker.PriceFeedReceiver;
import pl.sii.price.adapters.messagebroker.converter.CSVPriceFeedConverter;
import pl.sii.price.adapters.messagebroker.reader.CSVDataReader;
import pl.sii.price.adapters.repository.InMemoryPriceFeedRepository;
import pl.sii.price.domain.entity.PriceFeedCommissionService;
import pl.sii.price.domain.ports.PriceFeedService;

@Getter
public class MarketPriceHandlerExerciseConfig {

    private final PriceFeedReceiver priceFeedReceiver;
    
    private final PriceFeedEndpoint priceFeedEndpoint;
    
    public MarketPriceHandlerExerciseConfig() {
        PriceFeedService priceFeedService = new PriceFeedCommissionService(
                new InMemoryPriceFeedRepository(),
                0.001,
                4,
                RoundingMode.HALF_UP);
        
        priceFeedReceiver = new PriceFeedReceiver(
                priceFeedService,
                new CSVPriceFeedConverter(",", "dd-MM-yyyy HH:mm:ss:SSS"),
                new CSVDataReader());
        
        priceFeedEndpoint = new PriceFeedEndpoint(priceFeedService);
    }
}
