package com.gamesys.aggregate;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.gamesys.aggregate.app.AggregatorAppService;
import com.gamesys.aggregate.app.FileInput;
import com.google.common.base.Stopwatch;

@SpringBootApplication
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Autowired
    private AggregatorAppService aggregatorAppService;

    public static void main(String... args) {
        if (args.length == 4) {
            init(args);
        } else {
            System.out.println("Usage: aggregator.jar transactions.csv exchangerates.csv partner currency");
        }
    }

    private static void init(String[] args) {
        ApplicationContext springContext = SpringApplication.run(Main.class, args);
        Main main = springContext.getBean(Main.class);
        main.execute(new FileInput(args[0], args[1], args[2], args[3]));
    }

    private void execute(FileInput input) {
        LOG.info("Getting aggregated transactions by partner...");
        Stopwatch stopwatch = Stopwatch.createStarted();
        aggregatorAppService.processTransactionsByPartner(input);
        stopwatch.stop();
        LOG.info("Aggregated transactions by partner took {} msec", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        LOG.info("Getting aggregated transactions of partner...");
        stopwatch = Stopwatch.createStarted();
        aggregatorAppService.processTransactionsOfPartner(input);
        stopwatch.stop();
        LOG.info("Aggregated transactions of partner took {} msec", stopwatch.elapsed(TimeUnit.MILLISECONDS));

    }

}
