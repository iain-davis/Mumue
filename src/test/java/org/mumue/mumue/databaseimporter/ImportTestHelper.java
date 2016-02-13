package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.omg.CORBA.NamedValue;

class ImportTestHelper {
    private static final Random RANDOM = new Random();
    private static final String CHARACTER_FLAG = "3";
    private static final String EXIT_FLAG = "2";
    private static final String ROOM_FLAG = "0";
    private static final String PROGRAM_FLAG = "4";
    private static final String THING_FLAG = "1";

    public static List<String> generateLines(long randomParameters, String muckName, long startingLocation, int components) {
        List<String> lines = new ArrayList<>();
        lines.addAll(generateParameterLines(randomParameters, muckName, startingLocation));
        for (int i = 0; i < components; i++) {
            lines.addAll(generateOneComponent());
        }
        lines.add("***END OF DUMP***");
        return lines;
    }

    public static List<String> generateOneComponent() {
        FuzzballDatabaseItemType[] values = FuzzballDatabaseItemType.values();
        return generateOneComponent(values[RANDOM.nextInt(values.length)]);
    }

    public static List<String> generateOneComponent(FuzzballDatabaseItemType type) {
        List<String> lines = new ArrayList<>();
        lines.addAll(generateOneComponent(RandomStringUtils.randomNumeric(4), type, 0));
        return lines;
    }

    public static List<String> generateOneComponent(String id, FuzzballDatabaseItemType type, int propertyCount) {
        List<String> lines = new ArrayList<>();
        lines.add("#" + id);
        lines.addAll(generateRandomLines(4));
        lines.add(getFlagsFrom(type));
        lines.addAll(generateRandomLines(4));
        lines.add("*Props*");
        lines.addAll(generateRandomLines(propertyCount));
        lines.add("*End*");
        lines.addAll(generateRandomLines(type.getCodaSize()));
        return lines;
    }

    private static String getFlagsFrom(FuzzballDatabaseItemType type) {
        switch (type) {
            case CHARACTER:
                return CHARACTER_FLAG;
            case EXIT:
                return EXIT_FLAG;
            case PROGRAM:
                return PROGRAM_FLAG;
            case THING:
                return THING_FLAG;
            case ROOM:
                return ROOM_FLAG;
            default:
                return null;
        }
    }

    private static List<String> generateRandomLines(int lineCount) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add(RandomStringUtils.randomAlphabetic(13));
        }
        return lines;
    }

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
