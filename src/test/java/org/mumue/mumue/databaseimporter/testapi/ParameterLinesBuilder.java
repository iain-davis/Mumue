package org.mumue.mumue.databaseimporter.testapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

class ParameterLinesBuilder implements LineBuilder {
    public static final int REQUIRED_PARAMETER_COUNT = 2;
    private static final Random RANDOM = new Random();
    private String playerStart = RandomStringUtils.randomNumeric(3);
    private String muckName = RandomStringUtils.randomAlphabetic(25);
    private int additionalLineCount;

    @Override
    public List<String> getLines() {
        List<String> parameters = new ArrayList<>();
        for (int i = 0; i < additionalLineCount; i++) {
            parameters.add(RandomStringUtils.randomAlphanumeric(RANDOM.nextInt(13) + 3) + "=" + RandomStringUtils.randomAlphabetic(RANDOM.nextInt(13) + 5));
        }
        parameters.add("muckname=" + muckName);
        parameters.add("player_start=" + playerStart);
        Collections.shuffle(parameters, RANDOM);
        return parameters;
    }

    public ParameterLinesBuilder withPlayerStart(long start) {
        this.playerStart = String.valueOf(start);
        return this;
    }

    public ParameterLinesBuilder withMuckName(String muckName) {
        this.muckName = muckName;
        return this;
    }

    public ParameterLinesBuilder withAdditionalRandomParameters(int additionalLineCount) {
        this.additionalLineCount = additionalLineCount;
        return this;
    }
}
