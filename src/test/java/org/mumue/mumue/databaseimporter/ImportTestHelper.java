package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.NamedValue;

class ImportTestHelper {
    private static final Random RANDOM = new Random();

    public static List<String> generateParameterLines(long randomParameters) {
        return generateParameterLines(randomParameters, RandomStringUtils.randomAlphabetic(13), RANDOM.nextInt(1000));
    }

    public static List<String> generateParameterLines(long randomParameters, String muckName, long startingLocation, NamedValue... additionalParameters) {
        List<String> parameters = new ArrayList<>();
        for (int i = 0; i < randomParameters; i++) {
            parameters.add(RandomStringUtils.randomAlphanumeric(RANDOM.nextInt(13) + 3) + "=" +
                           RandomStringUtils.randomAlphabetic(RANDOM.nextInt(13) + 5));
        }
        for (NamedValue namedValue : additionalParameters) {
            parameters.add(namedValue.name() + "=" + namedValue.value());
        }
        parameters.add("muckname=" + muckName);
        parameters.add("player_start=" + String.valueOf(startingLocation));
        Collections.shuffle(parameters, RANDOM);
        return parameters;
    }
}
