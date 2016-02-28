package org.mumue.mumue.databaseimporter;

import java.time.Instant;
import java.util.List;

import org.mumue.mumue.components.Artifact;
import org.mumue.mumue.components.Link;
import org.mumue.mumue.components.LocatableComponent;
import org.mumue.mumue.components.MufProgram;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.universe.Universe;

class GameComponentImporter {
    protected static final int REFERENCE_ID_INDEX = 0;
    protected static final int NAME_INDEX = 1;
    protected static final int LOCATION_INDEX = 2;
    private static final int ITEM_FLAGS_INDEX = 5;
    private static final int CREATED_ON_TIMESTAMP_INDEX = 6;
    private static final int LAST_USED_ON_TIMESTAMP_INDEX = 7;
    private static final int USE_COUNT_INDEX = 8;
    private static final int LAST_MODIFIED_ON_TIMESTAMP_INDEX = 9;
    // *Props* = 10
    private static final int FIRST_PROPERTY_INDEX = 11;
    private static final String DESCRIPTION_LINE_PREFIX = "_/de:10:";

    public LocatableComponent importFrom(List<String> lines, Universe universe) {
        LocatableComponent component = createComponentAs(FuzzballDatabaseItemType.fromLine(lines.get(ITEM_FLAGS_INDEX)));
        if (component instanceof Garbage) {
            return component;
        }
        component.setId(importComponentReferenceFrom(lines.get(REFERENCE_ID_INDEX)));
        component.setCreated(importTimeStampFrom(lines.get(CREATED_ON_TIMESTAMP_INDEX)));
        component.setLastUsed(importTimeStampFrom(lines.get(LAST_USED_ON_TIMESTAMP_INDEX)));
        component.setUseCount(importIntegerFrom(lines.get(USE_COUNT_INDEX)));
        component.setLastModified(importTimeStampFrom(lines.get(LAST_MODIFIED_ON_TIMESTAMP_INDEX)));
        component.setName(lines.get(NAME_INDEX));
        component.setDescription(importDescriptionFrom(lines));
        component.setUniverseId(universe.getId());
        component.setLocationId(importIntegerFrom(lines.get(LOCATION_INDEX)));
        return component;
    }

    private long importComponentReferenceFrom(String line) {
        return importIntegerFrom(line.substring(1));
    }

    private long importIntegerFrom(String line) {
        return Long.parseLong(line);
    }

    private Instant importTimeStampFrom(String line) {
        return Instant.ofEpochSecond(Long.valueOf(line));
    }

    private String importDescriptionFrom(List<String> lines) {
        for (String line : lines.subList(FIRST_PROPERTY_INDEX, lines.size())) {
            if (line.startsWith(DESCRIPTION_LINE_PREFIX)) {
                return line.substring(DESCRIPTION_LINE_PREFIX.length());
            }
        }
        return null;
    }

    private LocatableComponent createComponentAs(FuzzballDatabaseItemType type) {
        switch (type) {
            case CHARACTER:
                return new GameCharacter();
            case EXIT:
                return new Link();
            case PROGRAM:
                return new MufProgram();
            case ROOM:
                return new Space();
            case THING:
                return new Artifact();
            default:
                return new Garbage();
        }
    }
}
