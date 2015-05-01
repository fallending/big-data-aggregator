package com.gamesys.aggregate.app.assemblers;

import com.gamesys.aggregate.domain.model.Currency;
import com.gamesys.aggregate.domain.model.Money;
import com.gamesys.aggregate.domain.model.Partner;
import com.gamesys.aggregate.domain.model.Transaction;
import com.google.common.base.Splitter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Component
public class TransactionAssembler implements Function<String, Transaction> {

    private static final String SEPARATOR = ",";
    private static final int PARTNER_INDEX = 0;
    private static final int AMOUNT_INDEX = 2;
    private static final int CURRENCY_INDEX = 1;

    @Override
    public Transaction apply(String line) {
        List<String> tokens = Splitter.on(SEPARATOR).splitToList(line);
        Partner partner = Partner.of(tokens.get(PARTNER_INDEX));
        Money money = Money.of(new BigDecimal(tokens.get(AMOUNT_INDEX)), Currency.of(tokens.get(CURRENCY_INDEX)));
        return Transaction.of(partner, money);
    }
}
