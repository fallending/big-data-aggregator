package com.gamesys.aggregate.integration;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gamesys.aggregate.Main;
import com.gamesys.aggregate.app.AggregatorAppService;
import com.gamesys.aggregate.app.FileInput;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
public class AggregatorEndToEndTest {

    @Autowired
    private AggregatorAppService appService;
    private FileInput fileInput;

    @Before
    public void setup() {
        String exchangeRatesPath = Paths.get("/Users/barnabas.szoke/dev/exchangerates.csv").toString();
        String transactionsPath = Paths.get("/Users/barnabas.szoke/dev/transactions.csv").toString();
        fileInput = new FileInput(exchangeRatesPath, transactionsPath, "Unlimited ltd", "GBP");
    }

    @Test
    public void testProcessTransactionsByPartner() {
        appService.processTransactionsByPartner(fileInput);
    }

    @Test
    public void testProcessTransactionsOfPartner() {
        appService.processTransactionsOfPartner(fileInput);
    }

}
