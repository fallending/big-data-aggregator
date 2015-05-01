package com.gamesys.aggregate.infrastructure.adapters.primary;

import com.gamesys.aggregate.app.assemblers.ExchangeRatesAssembler;
import com.gamesys.aggregate.app.assemblers.TransactionAssembler;
import com.gamesys.aggregate.domain.model.Currency;
import com.gamesys.aggregate.domain.model.Money;
import com.gamesys.aggregate.domain.model.Partner;
import com.gamesys.aggregate.domain.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileReaderServiceImplTest {

    private static final String TRANSACTIONS_CSV = "transactions.csv";
    private static final String EXCHANGE_RATES_CSV = "exchangerates.csv";

    @InjectMocks
    private FileReaderServiceImpl testObj = new FileReaderServiceImpl();

    @Mock
    private TransactionAssembler transactionAssembler;
    @Mock
    private ExchangeRatesAssembler exchangeRatesAssembler;
    @Captor
    private ArgumentCaptor<ArrayList<String>> captor;

    @Test
    public void testLoadTransactionsAsStream() throws IOException {
        // setup
        Path path = Paths.get(ClassLoader.getSystemResource(TRANSACTIONS_CSV).getPath());
        when(transactionAssembler.apply(any())).
                thenReturn(Transaction.of(Partner.of("Unlimited ltd"), Money.of(new BigDecimal(200.5), Currency.of("GPB"))));
        // act
        testObj.loadTransactionsAsStream(path).findFirst();

        // verify
        verify(transactionAssembler).apply("Unlimited ltd.,GPB,200.5");
    }

    @Test
    public void testLoadExchangeRates() {
        // setup
        Path path = Paths.get(ClassLoader.getSystemResource(EXCHANGE_RATES_CSV).getPath());

        // act
        testObj.loadExchangeRates(path);

        // assert
        verify(exchangeRatesAssembler).apply(captor.capture());
        assertEquals(4, captor.getValue().size());
    }
}