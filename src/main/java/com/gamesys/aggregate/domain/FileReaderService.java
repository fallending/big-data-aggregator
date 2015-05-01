package com.gamesys.aggregate.domain;

import com.gamesys.aggregate.domain.model.ExchangeRates;
import com.gamesys.aggregate.domain.model.Transaction;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileReaderService {
    Stream<Transaction> loadTransactionsAsStream(Path path);
    ExchangeRates loadExchangeRates(Path path);
}
