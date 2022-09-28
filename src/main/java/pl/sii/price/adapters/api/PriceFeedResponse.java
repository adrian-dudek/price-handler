package pl.sii.price.adapters.api;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import pl.sii.price.domain.entity.PriceFeed;

@Data
@Builder
public class PriceFeedResponse {

    private final BigDecimal sellPrice;
    
    private final BigDecimal buyPrice;
    
    public static PriceFeedResponse of(PriceFeed priceFeed) {
        return PriceFeedResponse.builder()
                .sellPrice(priceFeed.getSellPrice())
                .buyPrice(priceFeed.getBuyPrice())
                .build();
    }
}
