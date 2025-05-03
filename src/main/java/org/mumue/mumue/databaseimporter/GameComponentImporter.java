package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.components.LocatableComponent;
import org.mumue.mumue.components.universe.Universe;

import java.time.Instant;
import java.util.List;

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
    private final GameCharacterImporter characterImporter = new GameCharacterImporter();
    private final LinkImporter linkImporter = new LinkImporter();
    private final MufProgramImporter mufProgramImporter = new MufProgramImporter();
    private final SpaceImporter spaceImporter = new SpaceImporter();
    private final GarbageImporter garbageImporter = new GarbageImporter();
    private final ArtifactImporter artifactImporter = new ArtifactImporter();

    public LocatableComponent importFrom(List<String> lines, Universe universe) {
        LocatableComponent component = typeSpecificImport(lines);

        if (component instanceof Garbage) {
            return component;
        }
        component.setId(importComponentReferenceFrom(lines.getFirst()));
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

    private LocatableComponent typeSpecificImport(List<String> lines) {
        FuzzballDatabaseItemType type = FuzzballDatabaseItemType.fromLine(lines.get(ITEM_FLAGS_INDEX));
        return switch (type) {
            case CHARACTER -> characterImporter.importFrom(lines);
            case EXIT -> linkImporter.importFrom(lines);
            case PROGRAM -> mufProgramImporter.importFrom(lines);
            case ROOM -> spaceImporter.importFrom(lines);
            case THING -> artifactImporter.importFrom(lines);
            default -> garbageImporter.importFrom(lines);
        };
    }

    private long importComponentReferenceFrom(String line) {
        return importIntegerFrom(line.substring(1));
    }

    private long importIntegerFrom(String line) {
        return Long.parseLong(line);
    }

    private Instant importTimeStampFrom(String line) {
        return Instant.ofEpochSecond(Long.parseLong(line));
    }

    private String importDescriptionFrom(List<String> lines) {
        for (String line : lines.subList(FIRST_PROPERTY_INDEX, lines.size())) {
            if (line.startsWith(DESCRIPTION_LINE_PREFIX)) {
                return line.substring(DESCRIPTION_LINE_PREFIX.length());
            }
        }
        return null;
    }
}
