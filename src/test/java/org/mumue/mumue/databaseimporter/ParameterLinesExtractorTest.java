package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ParameterLinesExtractorTest {
    private final ParameterLinesExtractor extractor = new ParameterLinesExtractor();

    @Test
    public void neverReturnNull() {
        List<String> parameters = extractor.extract(new ArrayList<>());

        assertThat(parameters, notNullValue());
    }

    @Test
    public void extractParameterLines() {
        Integer parameterCount = new Random().nextInt(100);
        List<String> lines = new ArrayList<>();
        List<String> parameterLines = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            lines.add(RandomStringUtils.randomAlphabetic(5));
        }
        lines.add(parameterCount.toString());
        for (int i = 1; i <= parameterCount; i++) {
            String parameter = RandomStringUtils.randomAlphabetic(13);
            lines.add(parameter);
            parameterLines.add(parameter);
        }

        List<String> parameters = extractor.extract(lines);

        assertThat(parameters.size(), equalTo(parameterCount));
        for (String parameter : parameterLines) {
            assertThat(parameters, hasItem(parameter));
        }
    }
}