package com.gamesys.aggregate.domain.model;

import com.gamesys.aggregate.domain.model.Currency;
import com.gamesys.aggregate.domain.model.Money;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.assertEquals;

public class MoneyTest {

    private static final Currency CURRENCY_GBP = Currency.of("GBP");
    private static final Currency CURRENCY_USD = Currency.of("USD");
    private static final BigDecimal GBP_USD_EXCHANGE_RATE = new BigDecimal(1.49284);

    @Test
    public void addsTwoMoneyObjectsWhenTheCurrenciesAreTheSame() {
        // setup
        Money money1 = Money.of(new BigDecimal(10), CURRENCY_GBP);
        Money money2 = Money.of(new BigDecimal(20), CURRENCY_GBP);

        // act
        Money result = money1.add(money2);

        // assert
        assertEquals(new BigDecimal(30), result.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addsTwoMoneyObjectsWhenTheCurrenciesAreDifferentThrowsException() {
        // setup
        Money money1 = Money.of(new BigDecimal(10), CURRENCY_GBP);
        Money money2 = Money.of(new BigDecimal(20), CURRENCY_USD);

        // act
        money1.add(money2);
    }

    @Test(expected = NullPointerException.class)
    public void createMoneyIfTheCurrencyIsNullThrowsException() {
        // setup
        Money.of(new BigDecimal(10), null);
    }

    @Test
    public void convertTenGbpsToUsd() {
        // setup
        Money gbp = Money.of(BigDecimal.TEN, CURRENCY_GBP);
        Money expectedResult = Money.of(new BigDecimal(14.9284, MathContext.DECIMAL32), CURRENCY_USD);

        // act
        Money result = gbp.exchange(CURRENCY_USD, GBP_USD_EXCHANGE_RATE);

        // assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void convertGbpToGbpDoesntChangeTheAmount() {
        // setup
        Money gbp = Money.of(BigDecimal.TEN, CURRENCY_GBP);

        // act
        Money result = gbp.exchange(CURRENCY_GBP, GBP_USD_EXCHANGE_RATE);

        // assert
        assertEquals(gbp, result);
    }
}