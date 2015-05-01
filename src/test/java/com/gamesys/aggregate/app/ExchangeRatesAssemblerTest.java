package com.gamesys.aggregate.app;

import com.gamesys.aggregate.app.assemblers.ExchangeRatesAssembler;
import com.gamesys.aggregate.domain.model.Currency;
import com.gamesys.aggregate.domain.model.ExchangeRates;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ExchangeRatesAssemblerTest {

    private ExchangeRatesAssembler testObj = new ExchangeRatesAssembler();

    @Test
    public void testApply() throws Exception {
        // setup
        List<String> lines = Arrays.asList("HUF,GBP,0.0025", "USD,GBP,0.67");

        // act
        ExchangeRates result = testObj.apply(lines);

        // assert
        assertNotNull(result);
        assertEquals(2, result.getExchanges().size());
        BigDecimal exchangeRate = result.getExchangeRate(Currency.of("HUF"), Currency.of("GBP"));
        assertEquals(0, Double.valueOf(0.0025).compareTo(exchangeRate.doubleValue()));
    }
}