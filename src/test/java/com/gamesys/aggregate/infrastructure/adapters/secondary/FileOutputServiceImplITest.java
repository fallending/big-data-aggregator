package com.gamesys.aggregate.infrastructure.adapters.secondary;

import java.util.Map;

import org.junit.Test;

import com.gamesys.aggregate.domain.model.Partner;
import com.google.common.collect.Maps;

public class FileOutputServiceImplITest {

    private FileOutputServiceImpl testObj = new FileOutputServiceImpl();

    @Test
    public void testToOutputWritesToFile() {
        Map<Partner, Double> map = Maps.newHashMap();
        map.put(Partner.of("Unlimited ltd."), 311.25);
        map.put(Partner.of("Local plumber ltd."), 136.56);
        map.put(Partner.of("Defence ltd"), 234.75);

        // act
        testObj.toOutput(map);
    }

}