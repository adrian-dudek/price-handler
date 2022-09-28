package pl.sii.price.domain.entity;

public class PriceFeedNotExists extends RuntimeException {

    private static final long serialVersionUID = 2256887313384169586L;

    public PriceFeedNotExists(String message) {
        super(message);
    }
}
