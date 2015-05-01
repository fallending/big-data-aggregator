package com.gamesys.aggregate.app;

import lombok.Data;

@Data
public class FileInput {
    private final String exchangeRatesPath;
    private final String transactionsPath;
    private final String partner;
    private final String currency;
}
