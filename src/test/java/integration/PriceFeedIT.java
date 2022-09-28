package integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import pl.sii.price.adapters.api.PriceFeedResponse;
import pl.sii.price.adapters.messagebroker.converter.ConversionException;
import pl.sii.price.config.MarketPriceHandlerExerciseConfig;
import pl.sii.price.domain.entity.PriceFeedNotExists;

public class PriceFeedIT {

    private static MarketPriceHandlerExerciseConfig marketPriceHandlerExercise;
    
    private static ObjectMapper objectMapper;
    
    @BeforeAll
    public static void init() {
        marketPriceHandlerExercise = new MarketPriceHandlerExerciseConfig();        
        objectMapper = new ObjectMapper();
    }
    
    @Test
    void sendPriceFeedCorrectly() throws IOException {
        // given       
        String message = readFile("test-data.csv");
        List<ExpectedResult> expectedResults = objectMapper.readValue(
                readFile("expected-result.json"),
                new TypeReference<List<ExpectedResult>>(){});
        
        // when
        marketPriceHandlerExercise.getPriceFeedReceiver().onMessage(message);
        
        // then
        expectedResults.stream().forEach(this::checkExpectedResult);
    }
    
    @Test
    void sendWrongPriceFeed() throws IOException {
        // given       
        String message = "wrong data";
        
        // then
        assertThatException()
            .isThrownBy(() -> {
                // when
                marketPriceHandlerExercise.getPriceFeedReceiver().onMessage(message);
            })
            .isExactlyInstanceOf(ConversionException.class)
            .withMessage(String.format("Can't convert: %s into PriceFeed", message));
    }
    
    @Test
    void getWrongPriceFeed() throws IOException {
        // given       
        String instrumentName = "wrong data";
        
        // then
        assertThatException()            
            .isThrownBy(() -> {
                // when
                marketPriceHandlerExercise.getPriceFeedEndpoint().getTheLatestPriceFeed(instrumentName);
            })
            .isExactlyInstanceOf(PriceFeedNotExists.class)
            .withMessage(String.format("Can't find price feed for instrumentName: %s", instrumentName));
    }
    
    private void checkExpectedResult(ExpectedResult expectedResult) {
        PriceFeedResponse expectedResponse = PriceFeedResponse.builder()
                .buyPrice(expectedResult.getBuyPrice())
                .sellPrice(expectedResult.getSellPrice())
                .build();
        assertThat(marketPriceHandlerExercise.getPriceFeedEndpoint()
                .getTheLatestPriceFeed(expectedResult.getInstrumentName()))
                .isEqualTo(expectedResponse);
    }
    
    private String readFile(String path) throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        }
    }
}

@Data
class ExpectedResult {
    private String instrumentName;
    
    private BigDecimal buyPrice;
    
    private BigDecimal sellPrice;
}