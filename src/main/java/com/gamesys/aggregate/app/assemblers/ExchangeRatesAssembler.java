package com.gamesys.aggregate.app.assemblers;

import com.gamesys.aggregate.domain.model.Currency;
import com.gamesys.aggregate.domain.model.ExchangeRates;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class ExchangeRatesAssembler implements Function<List<String>, ExchangeRates> {

    private static final String SEPARATOR = ",";

    public ExchangeRates apply(List<String> lines) {
        Map<Pair<Currency, Currency>, BigDecimal> exchanges = Maps.newHashMap();

        lines.forEach(new Consumer<String>() {
            @Override
            public void accept(String line) {
                List<String> tokens = Splitter.on(SEPARATOR).splitToList(line);
                Pair<Currency, Currency> currencyPair = Pair.of(Currency.of(tokens.get(0)), Currency.of(tokens.get(1)));
                exchanges.put(currencyPair, new BigDecimal(tokens.get(2)));
            }
        });

        return new ExchangeRates(exchanges);
    }
}
