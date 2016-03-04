package org.mumue.mumue.databaseimporter.testapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class DatabaseLinesBuilderTest {
    private static final Random RANDOM = new Random();
    public static final int FORMAT_LINE_INDEX = 0;
    public static final int DATABASE_ITEM_COUNT_INDEX = 1;
    public static final int FORMAT_VERSION_INDEX = 2;
    public static final int PARAMETER_COUNT_INDEX = 3;
    public static final int FIRST_PARAMETER_INDEX = 4;
    private final MockParameterLinesBuilder EMPTY_PARAMETERS_BUILDER = new MockParameterLinesBuilder(Collections.emptyList());
    private final MockDatabaseItemLinesBuilder EMPTY_DATABASE_ITEM_BUILDER = new MockDatabaseItemLinesBuilder(Collections.emptyList());

    @Test
    public void firstLineIsFormat() {
        String format = RandomStringUtils.randomAlphabetic(13);
        List<String> lines = new DatabaseLinesBuilder(EMPTY_PARAMETERS_BUILDER, EMPTY_DATABASE_ITEM_BUILDER).withFormat(format).getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.get(FORMAT_LINE_INDEX), equalTo(format));
    }

    @Test
    public void formatDefault() {
        List<String> lines = new DatabaseLinesBuilder(EMPTY_PARAMETERS_BUILDER, EMPTY_DATABASE_ITEM_BUILDER).getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.get(FORMAT_LINE_INDEX), equalTo("***Foxen5 TinyMUCK DUMP Format***"));
    }

    @Test
    public void secondLineIsDatabaseItemCount() {
        int itemCount = RANDOM.nextInt(100) + 3;

        List<String> lines = new DatabaseLinesBuilder(EMPTY_PARAMETERS_BUILDER, EMPTY_DATABASE_ITEM_BUILDER).withDatabaseItemCount(itemCount).getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.get(DATABASE_ITEM_COUNT_INDEX), equalTo(String.valueOf(itemCount)));
    }

    @Test
    public void thirdLineIsFormatVersion() {
        String version = RandomStringUtils.randomAlphabetic(13);
        List<String> lines = new DatabaseLinesBuilder(EMPTY_PARAMETERS_BUILDER, EMPTY_DATABASE_ITEM_BUILDER).withFormatVersion(version).getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.get(FORMAT_VERSION_INDEX), equalTo(version));
    }

    @Test
    public void formatVersionDefault() {
        List<String> lines = new DatabaseLinesBuilder(EMPTY_PARAMETERS_BUILDER, EMPTY_DATABASE_ITEM_BUILDER).getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.get(FORMAT_VERSION_INDEX), equalTo("1"));
    }

    @Test
    public void fourthLineIsParameterCount() {
        List<String> lines = new DatabaseLinesBuilder(new ParameterLinesBuilder(), EMPTY_DATABASE_ITEM_BUILDER).getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.get(PARAMETER_COUNT_INDEX), equalTo(String.valueOf(ParameterLinesBuilder.REQUIRED_PARAMETER_COUNT)));
    }

    @Test
    public void parametersFollowParameterCount() {
        String property = RandomStringUtils.randomAlphanumeric(25);
        List<String> lines = new DatabaseLinesBuilder(new MockParameterLinesBuilder(Collections.singletonList(property)), EMPTY_DATABASE_ITEM_BUILDER).getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.get(FIRST_PARAMETER_INDEX), equalTo(property));
    }

    @Test
    public void includeOneDatabaseItem() {
        String dbItemIndicator = RandomStringUtils.randomAlphanumeric(25);
        List<String> lines = new DatabaseLinesBuilder(EMPTY_PARAMETERS_BUILDER, new MockDatabaseItemLinesBuilder(Collections.singletonList(dbItemIndicator)))
                .withDatabaseItemCount(1).getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.size(), greaterThan(4));
        assertThat(lines.get(FIRST_PARAMETER_INDEX), equalTo(dbItemIndicator));
    }

    @Test
    public void includeMultipleDatabaseItems() {
        String dbItemIndicator = RandomStringUtils.randomAlphanumeric(25);
        List<String> lines = new DatabaseLinesBuilder(EMPTY_PARAMETERS_BUILDER, new MockDatabaseItemLinesBuilder(Collections.singletonList(dbItemIndicator)))
                .withDatabaseItemCount(3)
                .getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.size(), greaterThan(6));
        assertThat(lines.get(FIRST_PARAMETER_INDEX), equalTo(dbItemIndicator));
        assertThat(lines.get(FIRST_PARAMETER_INDEX + 1), equalTo(dbItemIndicator));
        assertThat(lines.get(FIRST_PARAMETER_INDEX + 2), equalTo(dbItemIndicator));
    }

    @Test
    public void addsEndOfDumpMarker() {
        List<String> lines = new DatabaseLinesBuilder(EMPTY_PARAMETERS_BUILDER, EMPTY_DATABASE_ITEM_BUILDER).getLines();

        assertThat(lines, notNullValue());
        assertThat(lines.get(lines.size() - 1), equalTo("***END OF DUMP***"));
    }

    private class MockParameterLinesBuilder extends ParameterLinesBuilder {
        private final List<String> lines;

        private MockParameterLinesBuilder(List<String> lines) {
            this.lines = lines;
        }

        @Override
        public List<String> getLines() {
            return lines;
        }
    }

    private class MockDatabaseItemLinesBuilder extends DatabaseItemLinesBuilder {
        private final List<String> lines;

        private MockDatabaseItemLinesBuilder(List<String> lines) {
            this.lines = lines;
        }

        @Override
        public List<String> getLines() {
            return lines;
        }
    }
}