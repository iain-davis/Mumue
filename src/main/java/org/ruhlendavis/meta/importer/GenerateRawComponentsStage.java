package org.ruhlendavis.meta.importer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.components.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateRawComponentsStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        setupSpecialComponents(bucket);
        File file = new File(bucket.getFile());
        LineIterator iterator;
        String currentLine;
        try {
            iterator = FileUtils.lineIterator(file);
            while (iterator.hasNext()) {
                currentLine = iterator.nextLine();
                if ("***END OF DUMP***".equals(currentLine)) {
                    break;
                }

                String reference = skipParameterSection(iterator, currentLine);
                List<String> lines = new ArrayList<>();
                lines.add(reference);

                Long id = translateReference(reference);

                addLines(lines, iterator, 4);
                String line = iterator.nextLine();
                lines.add(line);
                long type = determineType(line);
                Component component = createComponent(type);
                addLines(lines, iterator, 5);
                addPropertyList(lines, iterator);
                addComponentSpecificLines(lines, iterator, type);
                component.setId(id);

                bucket.getComponents().put(id, component);
                bucket.getComponentLines().put(id, lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupSpecialComponents(ImportBucket bucket) {
        Component component = new Component();
        component.setId(GlobalConstants.REFERENCE_UNKNOWN);
        bucket.getComponents().put(GlobalConstants.REFERENCE_UNKNOWN, component);
        component = new Component();
        component.setId(GlobalConstants.REFERENCE_AMBIGUOUS);
        bucket.getComponents().put(GlobalConstants.REFERENCE_AMBIGUOUS, component);
        component = new Component();
        component.setId(GlobalConstants.REFERENCE_HOME);
        bucket.getComponents().put(GlobalConstants.REFERENCE_HOME, component);
    }

    private void addComponentSpecificLines(List<String> lines, LineIterator iterator, long type) {
        if (type == 0) {
            addLines(lines, iterator, 3);
        } else if (type == 1) {
            addLines(lines, iterator, 4);
        } else if (type == 2) {
            addLines(lines, iterator, Integer.parseInt(iterator.nextLine()) + 1);
        } else if (type == 3) {
            addLines(lines, iterator, 4);
        } else if (type == 4) {
            addLines(lines, iterator, 1);
        }
    }

    private void addLines(List<String> lines, LineIterator iterator, int count) {
        for (int i =0; i < count; i++) {
            lines.add(iterator.nextLine());
        }
    }

    private void addPropertyList(List<String> lines, LineIterator iterator) {
        String currentLine = "";
        while (iterator.hasNext()) {
            if ("*End*".equals(currentLine)) {
                break;
            }
            currentLine = iterator.nextLine();
            lines.add(currentLine);
        }
    }

    private Component createComponent(long type) {
        Component component;
        if (type == 0) {
            component = new Space();
        } else if (type == 1) {
            component = new Artifact();
        } else if (type == 2) {
            component = new Link();
        } else if (type == 3) {
            component = new org.ruhlendavis.meta.components.Character();
        } else if (type == 4) {
            component = new Program();
        } else {
            component = new Garbage();
        }
        return component;
    }

    private long determineType(String line) {
        return Long.parseLong(line.split(" ")[0]) & 0x7;
    }

    private String skipParameterSection(LineIterator inputIterator, String currentLine) {
        if ("***Foxen5 TinyMUCK DUMP Format***".equals(currentLine)) {
            while (inputIterator.hasNext() && !currentLine.matches("^#\\d+$")) {
                currentLine = inputIterator.nextLine();
            }
        }
        return currentLine;
    }
}
