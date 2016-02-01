package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.NamedValue;

class ImportTestHelper {
    private static final Random RANDOM = new Random();
    public static List<String> generateParameterLines(long randomParameters, NamedValue... additionalParameters) {
        List<String> parameters = new ArrayList<>();
        for (int i = 0; i < randomParameters; i++) {
            parameters.add(RandomStringUtils.randomAlphanumeric(RANDOM.nextInt(13) + 3) + "=" +
                           RandomStringUtils.randomAlphabetic(RANDOM.nextInt(13) + 5));
        }
        for (NamedValue namedValue : additionalParameters) {
            parameters.add(namedValue.name() + "=" + namedValue.value());
        }
        Collections.shuffle(parameters, RANDOM);
        return parameters;
    }
}
