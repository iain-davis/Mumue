package org.mumue.mumue.databaseimporter.testapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ParameterLinesBuilderTest {
    private static final Random RANDOM = new Random();

    @Test
    public void buildsPlayerStartParameter() {
        int start = RANDOM.nextInt(100) + 1;
        String expected = "player_start=" + String.valueOf(start);

        List<String> lines = new ParameterLinesBuilder().withPlayerStart(start).getLines();

        assertThat(lines, hasItem(expected));
    }

    @Test
    public void defaultPlayerStartParameter() {
        List<String> lines = new ParameterLinesBuilder().getLines();

        assertThat(lines, hasItem(startsWith("player_start=")));
        assertThat(lines, not(hasItem("player_start=null")));
    }

    @Test
    public void buildsMuckNameParameter() {
        String muckName = RandomStringUtils.randomAlphanumeric(25);
        String expected = "muckname=" + muckName;

        List<String> lines = new ParameterLinesBuilder().withMuckName(muckName).getLines();

        assertThat(lines, hasItem(expected));
    }

    @Test
    public void defaultMuckNameParameter() {
        List<String> lines = new ParameterLinesBuilder().getLines();

        assertThat(lines, hasItem(startsWith("muckname=")));
        assertThat(lines, not(hasItem("muckname=null")));
    }

    @Test
    public void withAdditionalRandomParameters() {
        int additionalLines = RANDOM.nextInt(100) + 3;
        List<String> lines = new ParameterLinesBuilder().withAdditionalRandomParameters(additionalLines).getLines();

        assertThat(lines.size(), equalTo(additionalLines + ParameterLinesBuilder.REQUIRED_PARAMETER_COUNT));
    }
}