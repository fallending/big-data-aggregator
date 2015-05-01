package com.gamesys.aggregate.app;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamesys.aggregate.domain.AggregatorFactory;
import com.gamesys.aggregate.domain.model.Currency;
import com.gamesys.aggregate.domain.model.Partner;
import com.gamesys.aggregate.domain.model.TransactionAggregate;

@Component
public class AggregatorAppServiceImpl implements AggregatorAppService {

    @Autowired
    private AggregatorFactory factory;

    @Override
    public void processTransactionsByPartner(FileInput input) {
        Currency currency = Currency.of(input.getCurrency());
        createAggregate(input).transactionsByPartner(currency);
    }

    @Override
    public void processTransactionsOfPartner(FileInput input) {
        Currency currency = Currency.of(input.getCurrency());
        Partner partner = Partner.of(input.getPartner());
        createAggregate(input).transactionsOfPartner(partner, currency);
    }

    private TransactionAggregate createAggregate(FileInput input) {
        return factory.createAggregate(Paths.get(input.getExchangeRatesPath()), Paths.get(input.getTransactionsPath()));
    }

}
