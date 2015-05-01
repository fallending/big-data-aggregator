package com.gamesys.aggregate.domain.model;

import com.gamesys.aggregate.domain.OutputService;
import com.gamesys.aggregate.domain.FileReaderService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionAggregateTest {

    private static final BigDecimal CHF_TO_GBP_EXCHANGE_RATE = new BigDecimal(0.71, MathContext.DECIMAL32);
    private static final BigDecimal USD_TO_GBP_EXCHANGE_RATE = new BigDecimal(0.67, MathContext.DECIMAL32);
    private static final Partner UNLIMITED_LTD = Partner.of("Unlimited ltd.");
    private static final Partner DEFENCE_LTD = Partner.of("Defence ltd");

    private static final Currency GBP_CURRENCY = Currency.of("GBP");
    private static final Currency USD_CURRENCY = Currency.of("USD");
    private static final Currency CHF_CURRENCY = Currency.of("CHF");

    private Path transactionFilePath = mock(Path.class);

    @Mock
    private ExchangeRates exchangeRates;

    @Mock
    private FileReaderService fileReaderService;

    @Mock
    private OutputService outputService;

    @InjectMocks
    private TransactionAggregate testObj = new TransactionAggregate(transactionFilePath, exchangeRates, fileReaderService, outputService, outputService);


    @Before
    public void setup() throws IOException {
        when(exchangeRates.getExchangeRate(CHF_CURRENCY, GBP_CURRENCY)).thenReturn(CHF_TO_GBP_EXCHANGE_RATE);
        when(exchangeRates.getExchangeRate(USD_CURRENCY, GBP_CURRENCY)).thenReturn(USD_TO_GBP_EXCHANGE_RATE);

        Transaction transaction1 = Transaction.of(UNLIMITED_LTD, Money.of(new BigDecimal(200.5, MathContext.DECIMAL32), GBP_CURRENCY));
        Transaction transaction2 = Transaction.of(DEFENCE_LTD, Money.of(new BigDecimal(350.3, MathContext.DECIMAL32), USD_CURRENCY));
        Transaction transaction3 = Transaction.of(UNLIMITED_LTD, Money.of(new BigDecimal(157.0, MathContext.DECIMAL32), CHF_CURRENCY));
        List<Transaction> transactions = Lists.newArrayList(transaction1, transaction2, transaction3);
        when(fileReaderService.loadTransactionsAsStream(transactionFilePath)).thenReturn(transactions.stream());
    }

    @Test
    public void testGetTransactionsByPartner()  {
        // act
        Map<Partner, Double> result = testObj.collectTransactionsByPartner(GBP_CURRENCY).get();

        // assert
        assertThat(result, hasEntry(UNLIMITED_LTD, 311.97));
        assertThat(result, hasEntry(DEFENCE_LTD, 234.701));
    }

    @Test
    public void testGetTransactionsOfPartner()  {
        // act
        Double result = testObj.collectTransactionsOfPartner(UNLIMITED_LTD, GBP_CURRENCY).get();

        // assert
        assertEquals(Double.valueOf(311.97), result);

    }
}