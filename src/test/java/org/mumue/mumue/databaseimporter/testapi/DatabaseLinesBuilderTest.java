package org.mumue.mumue.databaseimporter.testapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class DatabaseLinesBuilderTest {
    private static final Random RANDOM = new Random();

    @Test
    public void neverReturnNull() {
        DatabaseLinesBuilder linesBuilder = new DatabaseLinesBuilder(new ParameterLinesBuilder());
        assertThat(linesBuilder.getLines(), notNullValue());
    }

    @Test
    public void firstLineIsFormat() {
        String format = RandomStringUtils.randomAlphabetic(13);
        List<String> lines = new DatabaseLinesBuilder(new ParameterLinesBuilder()).withFormat(format).getLines();

        assertThat(lines.get(0), equalTo(format));
    }

    @Test
    public void formatDefault() {
        List<String> lines = new DatabaseLinesBuilder(new ParameterLinesBuilder()).getLines();

        assertThat(lines.get(0), equalTo("***Foxen5 TinyMUCK DUMP Format***"));
    }

    @Test
    public void thirdLineIsFormatVersion() {
        String version = RandomStringUtils.randomAlphabetic(13);
        List<String> lines = new DatabaseLinesBuilder(new ParameterLinesBuilder()).withFormatVersion(version).getLines();

        assertThat(lines.get(2), equalTo(version));
    }

    @Test
    public void formatVersionDefault() {
        List<String> lines = new DatabaseLinesBuilder(new ParameterLinesBuilder()).getLines();

        assertThat(lines.get(2), equalTo("1"));
    }

    @Test
    public void secondLineIsDatabaseItemCount() {
        int itemCount = RANDOM.nextInt(100) + 3;

        List<String> lines = new DatabaseLinesBuilder(new ParameterLinesBuilder()).withDatabaseItemCount(itemCount).getLines();

        assertThat(lines.get(1), equalTo(String.valueOf(itemCount)));
    }

    @Test
    public void fourthLineIsParameterCount() {
        List<String> lines = new DatabaseLinesBuilder(new ParameterLinesBuilder()).getLines();

        assertThat(lines.get(3), equalTo(String.valueOf(ParameterLinesBuilder.REQUIRED_PARAMETER_COUNT)));
    }
}