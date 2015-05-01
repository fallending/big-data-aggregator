package com.gamesys.aggregate.domain.model;

import com.gamesys.aggregate.domain.exception.DomainException;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ExchangeRates {
    private final Map<Pair<Currency, Currency>, BigDecimal> exchanges;

    public BigDecimal getExchangeRate(Currency fromCurrency, Currency toCurrency) {
        BigDecimal rate;
        if (fromCurrency.equals(toCurrency)) {
            rate = BigDecimal.ONE;
        } else {
            rate = exchanges.get(Pair.of(fromCurrency, toCurrency));
            if (rate == null) {
                throw new DomainException("Failed to convert " + fromCurrency.toString() + " to " + toCurrency.toString());
            }
        }
        return rate;
    }
}
