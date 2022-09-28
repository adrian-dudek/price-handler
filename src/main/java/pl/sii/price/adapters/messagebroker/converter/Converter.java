package pl.sii.price.adapters.messagebroker.converter;

public interface Converter<T> {

    T convert(String stringData) throws ConversionException;
}
