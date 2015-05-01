package com.gamesys.aggregate.domain.model;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.gamesys.aggregate.domain.OutputService;
import com.gamesys.aggregate.domain.FileReaderService;
import com.google.common.annotations.VisibleForTesting;

public class TransactionAggregate {

    private final Path transactionsFilePath;
    private ExchangeRates exchangeRates;
    private FileReaderService fileReaderService;
    private OutputService consoleOutputService;
    private OutputService fileOutputService;

    public TransactionAggregate(Path transactionsFilePath, ExchangeRates exchangeRates, FileReaderService fileReaderService, OutputService consoleOutputService, OutputService fileOutputService) {
        this.transactionsFilePath = transactionsFilePath;
        this.exchangeRates = exchangeRates;
        this.fileReaderService = fileReaderService;
        this.consoleOutputService = consoleOutputService;
        this.fileOutputService = fileOutputService;
    }

    @VisibleForTesting
    protected Optional<Map<Partner, Double>> collectTransactionsByPartner(final Currency currency) {
        Map<Partner, Double> result = fileReaderService.loadTransactionsAsStream(transactionsFilePath)
                .parallel()
                .collect(Collectors.groupingBy(t -> t.getPartner(),
                        Collectors.summingDouble(value -> {
                            BigDecimal exchangeRate = exchangeRates.getExchangeRate(value.getMoney().getCurrency(), currency);
                            return value.getMoney().exchange(currency, exchangeRate).getAmount().doubleValue();
                        })));
        return Optional.of(result);
    }

    @VisibleForTesting
    protected Optional<Double> collectTransactionsOfPartner(Partner partner, Currency currency) {
        Double result = fileReaderService.loadTransactionsAsStream(transactionsFilePath)
                .parallel()
                .filter(t -> t.getPartner().equals(partner))
                .map(t -> {
                    BigDecimal exchangeRate = exchangeRates.getExchangeRate(t.getMoney().getCurrency(), currency);
                    return t.getMoney().exchange(currency, exchangeRate);
                })
                .collect(Collectors.summingDouble(t -> t.getAmount().doubleValue()));
        return Optional.of(result);
    }

    public void transactionsOfPartner(Partner partner, Currency currency) {
        Optional<Double> result = collectTransactionsOfPartner(partner, currency);
        if (result.isPresent()) {
            consoleOutputService.toOutput(result.get());
        }
    }

    public void transactionsByPartner(Currency currency) {
        Optional<Map<Partner, Double>> result = collectTransactionsByPartner(currency);
        if (result.isPresent()) {
            fileOutputService.toOutput(result.get());
        }
    }

}
