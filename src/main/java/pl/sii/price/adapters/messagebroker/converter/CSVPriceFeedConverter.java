package pl.sii.price.adapters.messagebroker.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pl.sii.price.domain.entity.PriceFeed;

public class CSVPriceFeedConverter implements Converter<PriceFeed> {

    private final String delimiter;
    
    private final DateTimeFormatter dateTimeFormatter;
    
    public CSVPriceFeedConverter(String delimiter, String dateTimePattern) {
        this.delimiter = delimiter;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
    }
    
    @Override
    public PriceFeed convert(String priceFeedString) throws ConversionException {
        try {
            String[] fields = priceFeedString.split(delimiter);              
            return PriceFeed.builder()
                .instrumentName(fields[1])
                .sellPrice(new BigDecimal(fields[2]))
                .buyPrice(new BigDecimal(fields[3]))
                .timestamp(LocalDateTime.parse(fields[4], dateTimeFormatter))
                .build();
        }
        catch(Exception e) {
            throw new ConversionException(String.format("Can't convert: %s into PriceFeed", priceFeedString));
        }
    }

}
