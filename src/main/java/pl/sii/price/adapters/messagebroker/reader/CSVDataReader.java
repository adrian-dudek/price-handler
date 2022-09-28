package pl.sii.price.adapters.messagebroker.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.function.Consumer;

import pl.sii.price.adapters.messagebroker.converter.Converter;
import pl.sii.price.domain.entity.PriceFeed;

public class CSVDataReader implements DataReader<PriceFeed> {

    @Override
    public void process(String data, Converter<PriceFeed> converter, Consumer<PriceFeed> action) throws IOException {
        try (BufferedReader reader = new BufferedReader(new StringReader(data))) {
            reader.lines().map(converter::convert).forEach(action);
        }
    }
}