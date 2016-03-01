package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.universe.Universe;

class ComponentsImporter {
    private static final int ITEM_FLAGS_INDEX = 5;
    private final GameComponentImporter gameComponentImporter;

    ComponentsImporter(GameComponentImporter gameComponentImporter) {
        this.gameComponentImporter = gameComponentImporter;
    }

    public List<Component> importFrom(List<String> lines, Universe universe) {
        List<Component> components = importComponents(lines, universe);
        components.addAll(generatePlayers(components));
        return components;
    }

    private List<ImportPlayer> generatePlayers(List<Component> components) {
        return components.stream()
                .filter(ImportCharacter.class::isInstance)
                .map(character -> new ImportPlayer((ImportCharacter) character))
                .collect(Collectors.toList());
    }

    private List<Component> importComponents(List<String> lines, Universe universe) {
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
