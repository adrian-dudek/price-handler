package pl.sii.price.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceFeed {

    private String instrumentName;
    
    private BigDecimal buyPrice;
    
    private BigDecimal sellPrice;
    
    private LocalDateTime timestamp;    
}
