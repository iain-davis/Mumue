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
import org.mumue.mumue.databaseimporter.testapi.ParameterLinesBuilder;

public class ParametersParserTest {
    private static final Random RANDOM = new Random();
    private final ParametersParser extractor = new ParametersParser();

    @Test
    public void neverReturnNull() {
        Properties parameters = extractor.importFrom(new ArrayList<>());

        assertThat(parameters, notNullValue());
    }

    @Test
    public void extractParameterLines() {
        List<String> lines = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            lines.add(RandomStringUtils.insecure().nextAlphabetic(5));
        }
        List<String> parameterLines = new ParameterLinesBuilder().withAdditionalRandomParameters(RANDOM.nextInt(100) + 1).getLines();
        lines.add(String.valueOf(parameterLines.size()));
        lines.addAll(parameterLines);
        Properties parameters = extractor.importFrom(lines);

        assertThat(parameters.size(), equalTo(parameterLines.size()));
        for (String parameter : parameterLines) {
            String name = parameter.split("=")[0];
            String value = parameter.split("=")[1];
            assertThat(parameters.get(name), equalTo(value));
        }
    }
}
