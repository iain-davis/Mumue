package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.List;

import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.space.Space;

class ComponentImporter {
    private static final int ITEM_FLAGS_INDEX = 5;

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
                return new GameCharacter();
            case PROGRAM:
                return null;
            case ROOM:
                return new Space();
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
