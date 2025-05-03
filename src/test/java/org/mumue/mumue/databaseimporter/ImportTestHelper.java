package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

class ImportTestHelper {
    private static final Random RANDOM = new Random();
    private static final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();

    public static List<String> generateLines(long randomParameters, String muckName, long startingLocation, int components) {
        List<String> parameterLines = generateParameterLines(randomParameters, muckName, startingLocation);
        List<String> lines = new ArrayList<>(parameterLines);
        for (int i = 0; i < components; i++) {
            long id = i;
            if (i > 1) id = id * 2;
            lines.addAll(databaseItemLinesBuilder.withId(id).build());
        }
        lines.add("***END OF DUMP***");
        return lines;
    }

    public static List<String> generateParameterLines(long randomParameters) {
        return generateParameterLines(randomParameters, RandomStringUtils.insecure().nextAlphabetic(13), RANDOM.nextInt(1000));
    }

    public static List<String> generateParameterLines(long randomParameters, String muckName, long startingLocation) {
        List<String> parameters = new ArrayList<>();
        for (int i = 0; i < randomParameters; i++) {
            parameters.add(RandomStringUtils.insecure().nextAlphanumeric(RANDOM.nextInt(13) + 3) + "=" +
                           RandomStringUtils.insecure().nextAlphabetic(RANDOM.nextInt(13) + 5));
        }
        parameters.add("muckname=" + muckName);
        parameters.add("player_start=" + String.valueOf(startingLocation));
        Collections.shuffle(parameters, RANDOM);
        return parameters;
    }
}
