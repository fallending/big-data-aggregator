package com.gamesys.aggregate.app;

public interface AggregatorAppService {

    void processTransactionsByPartner(FileInput input);

    void processTransactionsOfPartner(FileInput input);
}
