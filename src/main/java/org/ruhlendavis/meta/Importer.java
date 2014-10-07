package org.ruhlendavis.meta;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.ruhlendavis.meta.components.Component;
import org.ruhlendavis.meta.components.Garbage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importer {
    public void importFromGlow(String file) {
        File inputFile = new File(file);
        List<Component> components = new ArrayList<>();
        try {
            LineIterator inputIterator = FileUtils.lineIterator(inputFile);
            List<String> lines = new ArrayList<String>();
            String currentLine;
            ComponentBuilder builder = new ComponentBuilder();
            while (inputIterator.hasNext()) {
                currentLine = inputIterator.nextLine();
                if (currentLine.matches("^#\\d+$")) {
                    if (!lines.isEmpty() && !lines.get(0).equals("***Foxen5 TinyMUCK DUMP Format***")) {
                        Component component = builder.generate(lines);
                        if (!(component instanceof Garbage)) {
                            components.add(component);
                        }
                        lines = new ArrayList<String>();
                    }
                }
                lines.add(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Component c : components) {
            System.out.println(c.getName());
        }
    }
}
