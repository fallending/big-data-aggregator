package com.gamesys.aggregate.infrastructure.adapters.primary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gamesys.aggregate.app.assemblers.ExchangeRatesAssembler;
import com.gamesys.aggregate.app.assemblers.TransactionAssembler;
import com.gamesys.aggregate.domain.FileReaderService;
import com.gamesys.aggregate.domain.exception.DomainException;
import com.gamesys.aggregate.domain.model.ExchangeRates;
import com.gamesys.aggregate.domain.model.Transaction;
import com.google.common.collect.Lists;

@Component
public class FileReaderServiceImpl implements FileReaderService {

    @Autowired
    private TransactionAssembler transactionAssembler;
    @Autowired
    private ExchangeRatesAssembler exchangesRateAssembler;

    @Override
    public Stream<Transaction> loadTransactionsAsStream(Path path) {
        try {
            return Files.lines(path).parallel().map(line -> transactionAssembler.apply(line));
        } catch (IOException e) {
            throw new DomainException("Failed to read file: " + path.toString(), e);
        }
    }

    @Override
    public ExchangeRates loadExchangeRates(Path path) {
        List<String> result = Lists.newArrayList();
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(s -> result.add(s));
            return exchangesRateAssembler.apply(result);
        } catch (Exception e) {
            throw new DomainException("Failed to read file: " + path.toString(), e);
        }
    }
}
