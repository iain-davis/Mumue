package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.List;

import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.universe.Universe;

class ComponentsImporter {
    private static final int ITEM_FLAGS_INDEX = 5;
    private final GameComponentImporter gameComponentImporter;

    ComponentsImporter(GameComponentImporter gameComponentImporter) {
        this.gameComponentImporter = gameComponentImporter;
    }

    public List<Component> importFrom(List<String> lines, Universe universe) {
        List<Component> components = new ArrayList<>();
        while (!lines.isEmpty()) {
            int componentLineCount = calculateNumberOfComponentLines(lines);
            components.add(gameComponentImporter.importFrom(lines.subList(0, componentLineCount), universe));
            for (int i = 0; i < componentLineCount; i++) {
                lines.remove(0);
            }
        }
        return components;
    }

    private int calculateNumberOfComponentLines(List<String> lines) {
        FuzzballDatabaseItemType type = FuzzballDatabaseItemType.fromLine(lines.get(ITEM_FLAGS_INDEX));
        int count = 0;
        for (String line : lines) {
            count++;
            if (line.equals("*End*")) {
                break;
            }
        }
        return count + type.getCodaSize();
    }
}
