package org.mumue.mumue.databaseimporter.testapi;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.mumue.mumue.databaseimporter.FuzzballDatabaseItemType;
import org.mumue.mumue.importer.GlobalConstants;

public class DatabaseItemLinesBuilder implements LineBuilder {
    private static final Random RANDOM = new Random();
    private static final String CHARACTER_FLAG = "3";
    private static final String EXIT_FLAG = "2";
    private static final String ROOM_FLAG = "0";
    private static final String PROGRAM_FLAG = "4";
    private static final String THING_FLAG = "1";
    private static final String GARBAGE_FLAG = "5";
    private long id = GlobalConstants.REFERENCE_UNKNOWN;
    private String name = RandomStringUtils.insecure().nextAlphanumeric(25);
    private long locationId = GlobalConstants.REFERENCE_UNKNOWN;
    private FuzzballDatabaseItemType type = randomItemType();
    private int propertyCount = RANDOM.nextInt(20) + 10;
    private Instant createdOn = Instant.ofEpochSecond(0);
    private Instant lastUsed = Instant.ofEpochSecond(0);
    private Instant lastModified = Instant.ofEpochSecond(0);
    private long useCount = -1;
    private String description = "";
    private long homeId = GlobalConstants.REFERENCE_UNKNOWN;
    private String password = "";

    @Override
    public List<String> getLines() {
        List<String> lines = new ArrayList<>();
        lines.add("#" + id);                                               // 0 - Reference
        lines.add(name);                                                   // 1 - Name
        lines.add(String.valueOf(locationId));                             // 2 - Location Reference
        lines.addAll(generateRandomLines(2));                              // 3 - Contents, 4 - Next
        lines.add(getFlagsFrom(type));                                     // 5 - Flags
        lines.add(generateTimestampFrom(createdOn));                       // 6 - Created On
        lines.add(generateTimestampFrom(lastUsed));                        // 7 - Last Used
        lines.add(String.valueOf(useCount));                               // 8 - Use Count
        lines.add(generateTimestampFrom(lastModified));                    // 9 - Last Modified
        lines.add("*Props*");                                              // 10
        lines.add("_/de:10:" + description);
        lines.addAll(generateRandomLines(propertyCount));                  // 11 ... (10 + number of props) - properties
        lines.add("*End*");                                                // (11 + number of props)
        lines.addAll(generateCodaLines(type));             // ...
        return lines;
    }

    private List<String> generateCodaLines(FuzzballDatabaseItemType type) {
        switch (type) {
            case CHARACTER:
                return generateCharacterCodaLines();
            case EXIT:
                return generateRandomLines(type.getCodaSize());
            case GARBAGE:
                return generateRandomLines(type.getCodaSize());
            case PROGRAM:
                return generateRandomLines(type.getCodaSize());
            case ROOM:
                return generateRandomLines(type.getCodaSize());
            case THING:
                return generateRandomLines(type.getCodaSize());
            default:
                return generateRandomLines(type.getCodaSize());
        }
    }

    private List<String> generateCharacterCodaLines() {
        List<String> lines = new ArrayList<>();
        lines.add(String.valueOf(homeId));
        lines.addAll(generateRandomLines(type.getCodaSize() - 2));
        lines.add(password);
        return lines;
    }

    private String generateTimestampFrom(Instant instant) {
        return String.valueOf(instant.getEpochSecond());
    }

    private List<String> generateRandomLines(int lineCount) {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < lineCount; i++) {
            lines.add(RandomStringUtils.insecure().nextAlphabetic(13));
        }
        return lines;
    }

    private String getFlagsFrom(FuzzballDatabaseItemType type) {
        switch (type) {
            case CHARACTER:
                return CHARACTER_FLAG;
            case EXIT:
                return EXIT_FLAG;
            case GARBAGE:
                return GARBAGE_FLAG;
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

    public DatabaseItemLinesBuilder withRandomType() {
        this.type = randomItemType();
        return this;
    }

    private FuzzballDatabaseItemType randomItemType() {
        List<FuzzballDatabaseItemType> itemTypes = EnumUtils.getEnumList(FuzzballDatabaseItemType.class);
        itemTypes.remove(FuzzballDatabaseItemType.GARBAGE);
        return itemTypes.get(RANDOM.nextInt(itemTypes.size()));
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
        this.id = RandomUtils.insecure().randomInt(0, 1000);
        return this;
    }

    public DatabaseItemLinesBuilder createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public DatabaseItemLinesBuilder lastUsedOn(Instant lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public DatabaseItemLinesBuilder lastModifiedOn(Instant lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public DatabaseItemLinesBuilder withUseCount(long useCount) {
        this.useCount = useCount;
        return this;
    }

    public DatabaseItemLinesBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public DatabaseItemLinesBuilder withHomeId(long homeId) {
        this.homeId = homeId;
        return this;
    }

    public DatabaseItemLinesBuilder withPassword(String password) {
        this.password = password;
        return this;
    }
}
