package org.ruhlendavis.meta;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.ruhlendavis.meta.components.Component;
import org.ruhlendavis.meta.components.Link;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importer {
    public List<Component> importFromGlow(String file) {
        File inputFile = new File(file);
        List<Component> components = new ArrayList<>();
        try {
            LineIterator inputIterator = FileUtils.lineIterator(inputFile);
            List<String> lines = new ArrayList<>();
            String currentLine;
            ComponentBuilder builder = new ComponentBuilder();
            while (inputIterator.hasNext()) {
                currentLine = inputIterator.nextLine();
                if ("***END OF DUMP***".equals(currentLine))
                {
                    break;
                }

                // Parameter Section
                if ("***Foxen5 TinyMUCK DUMP Format***".equals(currentLine)) {
                    while (inputIterator.hasNext() && !currentLine.matches("^#\\d+$")) {
                        currentLine = inputIterator.nextLine();
                    }
                }

                // Database Item database reference and preamble
                while (inputIterator.hasNext() && !"*Props*".equals(currentLine)) {
                    lines.add(currentLine);
                    currentLine = inputIterator.nextLine();
                }

                while (inputIterator.hasNext()) {
                    lines.add(currentLine);
                    if ("*End*".equals(currentLine)) {
                        break;
                    }
                    currentLine = inputIterator.nextLine();
                }

                // Type Specific Postamble here.
                long type = Long.parseLong(lines.get(5).split(" ")[0]) & 0x7;
                if (type == 0) { // Space/Room
                    lines.add(inputIterator.nextLine());
                    lines.add(inputIterator.nextLine());
                    lines.add(inputIterator.nextLine());
                } else if (type == 1) {
                    lines.add(inputIterator.nextLine());
                    lines.add(inputIterator.nextLine());
                    lines.add(inputIterator.nextLine());
                    lines.add(inputIterator.nextLine());
                } else if (type == 2) {
                    lines.add(inputIterator.nextLine());
                    int destinationCount = Integer.parseInt(lines.get(lines.size() - 1));
                    for (int i = 0; i < destinationCount; i++) {
                        lines.add(inputIterator.nextLine());
                    }
                    lines.add(inputIterator.nextLine());
                } else if (type == 3) {
                    lines.add(inputIterator.nextLine());
                    lines.add(inputIterator.nextLine());
                    lines.add(inputIterator.nextLine());
                    lines.add(inputIterator.nextLine());
                } else if (type == 4) {
                    lines.add(inputIterator.nextLine());
                }
                Component component = builder.generate(lines);
                System.out.println("#" + component.getId() + ": " + component.getName());
                components.add(component);
                lines.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Component component : components) {
            if (component instanceof Link) {
                Link link = (Link) component;
                List<Long> remove = new ArrayList<>();
                for (Long id : link.getDestinationIds()) {
                    if (id.equals(-3L)) {
                        continue;
                    }
                    boolean found = false;
                    for (Component innerComponent : components) {
                        if (innerComponent.getId().equals(id)) {
                            link.getDestinations().add(innerComponent);
                            remove.add(id);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Warning: Link id #" + link.getId() + " had non-existent destination id #" + id);
                    }
                }
                link.getDestinationIds().removeAll(remove);
            }
        }

        return components;
    }
}
