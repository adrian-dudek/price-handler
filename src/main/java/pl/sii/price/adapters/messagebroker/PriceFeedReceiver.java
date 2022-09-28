package pl.sii.price.adapters.messagebroker;

import java.io.IOException;

import pl.sii.price.adapters.messagebroker.converter.Converter;
import pl.sii.price.adapters.messagebroker.reader.DataReader;
import pl.sii.price.domain.entity.PriceFeed;
import pl.sii.price.domain.ports.PriceFeedService;

public class PriceFeedReceiver {

    private final PriceFeedService priceFeedService;
    
    private final DataReader<PriceFeed> dataReader;
    
    private final Converter<PriceFeed> priceFeedConverter;
    
    public PriceFeedReceiver(PriceFeedService priceFeedService, Converter<PriceFeed> priceFeedConverter,
            DataReader<PriceFeed> dataReader) {
        this.priceFeedService = priceFeedService;
        this.priceFeedConverter = priceFeedConverter;
        this.dataReader = dataReader;
    }
    
    public void onMessage(String message) throws IOException {
        dataReader.process(message, priceFeedConverter, priceFeedService::add);
    }
}
