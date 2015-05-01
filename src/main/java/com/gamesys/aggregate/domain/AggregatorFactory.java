package com.gamesys.aggregate.domain;

import java.nio.file.Path;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamesys.aggregate.domain.model.ExchangeRates;
import com.gamesys.aggregate.domain.model.Partner;
import com.gamesys.aggregate.domain.model.TransactionAggregate;

@Component
public class AggregatorFactory {

    @Autowired
    private FileReaderService fileReaderService;

    @Autowired
    private OutputService<Double> consoleService;

    @Autowired
    private OutputService<Map<Partner, Double>> fileWriterService;

    public TransactionAggregate createAggregate(Path transactionsPath, Path exchangesPath) {
        ExchangeRates exchangeRates = fileReaderService.loadExchangeRates(exchangesPath);
        return new TransactionAggregate(transactionsPath, exchangeRates, fileReaderService, consoleService, fileWriterService);
    }
}
