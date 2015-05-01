package com.gamesys.aggregate.infrastructure.adapters.secondary;

import com.gamesys.aggregate.domain.OutputService;
import com.gamesys.aggregate.domain.exception.DomainException;
import com.gamesys.aggregate.domain.model.Partner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.function.BiConsumer;

@Component
public class FileOutputServiceImpl implements OutputService<Map<Partner, Double>> {

    private static final String OUTPUT_FILENAME = "aggregated_transactions_by_partner.csv";

    @Override
    public void toOutput(Map<Partner, Double> result) {
        Path path = Paths.get(OUTPUT_FILENAME);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {

            result.forEach(new BiConsumer<Partner, Double>() {
                @Override
                public void accept(Partner partner, Double value) {
                    try {
                        StringBuilder builder = new StringBuilder()
                                .append(partner.getName())
                                .append(",")
                                .append(value.toString())
                                .append("\n");
                        writer.write(builder.toString());
                    } catch (IOException e) {
                        throw new DomainException();
                    }
                }
            });

        } catch (Exception e) {
            throw new DomainException("Failed to write file: " + path.toString(), e);
        }

    }
}
