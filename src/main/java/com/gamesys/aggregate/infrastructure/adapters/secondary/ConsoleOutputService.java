package com.gamesys.aggregate.infrastructure.adapters.secondary;

import com.gamesys.aggregate.domain.OutputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputService implements OutputService<Double> {

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleOutputService.class);

    @Override
    public void toOutput(Double result) {
        LOG.info(result.toString());
    }
}
