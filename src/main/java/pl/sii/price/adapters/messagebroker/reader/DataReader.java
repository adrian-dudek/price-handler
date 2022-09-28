package pl.sii.price.adapters.messagebroker.reader;

import java.io.IOException;
import java.util.function.Consumer;

import pl.sii.price.adapters.messagebroker.converter.Converter;

public interface DataReader<T> {

    void process(String data, Converter<T> converter, Consumer<T> action) throws IOException;
}
