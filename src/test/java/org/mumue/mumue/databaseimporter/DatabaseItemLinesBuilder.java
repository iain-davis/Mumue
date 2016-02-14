package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.mumue.mumue.importer.GlobalConstants;

class DatabaseItemLinesBuilder {
    private static final Random RANDOM = new Random();
    private static final String CHARACTER_FLAG = "3";
    private static final String EXIT_FLAG = "2";
    private static final String ROOM_FLAG = "0";
    private static final String PROGRAM_FLAG = "4";
    private static final String THING_FLAG = "1";
    private long id = GlobalConstants.REFERENCE_UNKNOWN;
    private String name = RandomStringUtils.randomAlphanumeric(25);
    private long locationId = GlobalConstants.REFERENCE_UNKNOWN;
    private FuzzballDatabaseItemType type = FuzzballDatabaseItemType.values()[RANDOM.nextInt(FuzzballDatabaseItemType.values().length)];
    private int propertyCount = RANDOM.nextInt(20) + 10;

    List<String> build() {
        List<String> lines = new ArrayList<>();
        lines.add("#" + id);
        lines.add(name);
        lines.add(String.valueOf(locationId));
        lines.addAll(generateRandomLines(2));
        lines.add(getFlagsFrom(type));
        lines.addAll(generateRandomLines(4));
        lines.add("*Props*");
        lines.addAll(generateRandomLines(propertyCount));
        lines.add("*End*");
        lines.addAll(generateRandomLines(type.getCodaSize()));
        return lines;
    }

    private List<String> generateRandomLines(int lineCount) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add(RandomStringUtils.randomAlphabetic(13));
        }
        return lines;
    }

    private String getFlagsFrom(FuzzballDatabaseItemType type) {
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

    public DatabaseItemLinesBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public DatabaseItemLinesBuilder withType(FuzzballDatabaseItemType type) {
        this.type = type;
        return this;
    }

    public DatabaseItemLinesBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DatabaseItemLinesBuilder withLocationId(long locationId) {
        this.locationId = locationId;
        return this;
    }

    public DatabaseItemLinesBuilder withRandomId() {
        this.id = RANDOM.nextInt(10000);
        return this;
    }
}
