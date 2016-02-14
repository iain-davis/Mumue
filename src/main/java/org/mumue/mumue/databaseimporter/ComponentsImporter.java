package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.mumue.mumue.components.Component;

class ComponentsImporter {
    private static final int ITEM_FLAGS_INDEX = 5;
    private final ArtifactImporter artifactImporter;
    private final CharacterImporter characterImporter;
    private final LinkImporter linkImporter;
    private final ProgramImporter programImporter;
    private final SpaceImporter spaceImporter;

    @Inject
    ComponentsImporter(ArtifactImporter artifactImporter, CharacterImporter characterImporter, LinkImporter linkImporter, ProgramImporter programImporter, SpaceImporter spaceImporter) {
        this.artifactImporter = artifactImporter;
        this.characterImporter = characterImporter;
        this.linkImporter = linkImporter;
        this.programImporter = programImporter;
        this.spaceImporter = spaceImporter;
    }

    public List<Component> importFrom(List<String> lines) {
        List<Component> components = new ArrayList<>();
        while (!lines.isEmpty()) {
            FuzzballDatabaseItemType type = FuzzballDatabaseItemType.fromLine(lines.get(ITEM_FLAGS_INDEX));
            int count = countToEndOfProperties(lines) + type.getCodaSize();
            List<String> componentLines = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                componentLines.add(lines.get(0));
                lines.remove(0);
            }

            components.add(importComponentFrom(componentLines, type));
        }
        return components;
    }

    private Component importComponentFrom(List<String> componentLines, FuzzballDatabaseItemType type) {
        switch (type) {
            case CHARACTER:
                return characterImporter.importFrom(componentLines);
            case EXIT:
                return linkImporter.importFrom(componentLines);
            case PROGRAM:
                return programImporter.importFrom(componentLines);
            case ROOM:
                return spaceImporter.importFrom(componentLines);
            case THING:
                return artifactImporter.importFrom(componentLines);
            default:
                return null;
        }
    }

    private int countToEndOfProperties(List<String> lines) {
        int count = 0;
        for (String line : lines) {
            count++;
            if (line.equals("*End*")) {
                break;
            }
        }
        return count;
    }
}
