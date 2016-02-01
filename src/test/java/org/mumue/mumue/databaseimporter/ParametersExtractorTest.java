package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ParametersExtractorTest {
    private final ParametersExtractor extractor = new ParametersExtractor();

    @Test
    public void neverReturnNull() {
        Properties parameters = extractor.extract(new ArrayList<>());

        assertThat(parameters, notNullValue());
    }

    @Test
    public void extractParameterLines() {
        Integer parameterCount = new Random().nextInt(100);
        List<String> lines = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            lines.add(RandomStringUtils.randomAlphabetic(5));
        }
        lines.add(parameterCount.toString());
        List<String> parameterLines = ImportTestHelper.generateParameterLines(parameterCount);
        lines.addAll(parameterLines);
        Properties parameters = extractor.extract(lines);

        assertThat(parameters.size(), equalTo(parameterCount));
        for (String parameter : parameterLines) {
            String name = parameter.split("=")[0];
            String value = parameter.split("=")[1];
            assertThat(parameters.get(name), equalTo(value));
        }
    }
}