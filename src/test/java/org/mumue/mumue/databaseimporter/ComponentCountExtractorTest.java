package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ComponentCountExtractorTest {
    private final ComponentCountExtractor extractor = new ComponentCountExtractor();

    @Test
    public void handleNoLines() {
        int count = extractor.extract(new ArrayList<>());

        assertThat(count, equalTo(0));
    }

    @Test
    public void extractCount() {
        int expected = new Random().nextInt(1000);
        List<String> lines = new ArrayList<>();
        lines.add(RandomStringUtils.randomAlphabetic(13));
        lines.add(String.valueOf(expected));
        int count = extractor.extract(lines);

        assertThat(count, equalTo(expected));
    }
}