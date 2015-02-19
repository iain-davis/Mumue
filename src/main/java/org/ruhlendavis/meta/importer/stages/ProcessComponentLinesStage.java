package org.ruhlendavis.meta.importer.stages;

import java.time.Instant;
import java.util.List;
import java.util.Map.Entry;

import org.ruhlendavis.meta.components.Component;
import org.ruhlendavis.meta.componentsold.Artifact;
import org.ruhlendavis.meta.componentsold.GameCharacter;
import org.ruhlendavis.meta.componentsold.Link;
import org.ruhlendavis.meta.componentsold.LinkSource;
import org.ruhlendavis.meta.componentsold.Ownable;
import org.ruhlendavis.meta.componentsold.Program;
import org.ruhlendavis.meta.componentsold.Space;
import org.ruhlendavis.meta.componentsold.properties.IntegerProperty;
import org.ruhlendavis.meta.componentsold.properties.LockProperty;
import org.ruhlendavis.meta.componentsold.properties.ReferenceProperty;
import org.ruhlendavis.meta.componentsold.properties.StringProperty;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStage;

public class ProcessComponentLinesStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        for (Entry<Long, Component> entry : bucket.getComponents().entrySet()) {
            processComponent(bucket, entry.getValue());
        }
    }

    private void processComponent(ImportBucket bucket, Component component) {
        List<String> lines = bucket.getComponentLines().get(component.getId());
        component.setName(lines.get(NAME_INDEX));
        component.setLocation(getComponent(bucket, lines.get(LOCATION_INDEX)));
        component.getContents().add(getComponent(bucket, lines.get(CONTENTS_INDEX)));
        component.setCreated(Instant.ofEpochSecond(Long.parseLong(lines.get(CREATED_INDEX))));
        component.setLastUsed(Instant.ofEpochSecond(Long.parseLong(lines.get(LAST_USED_INDEX))));
        component.setUseCount(Long.parseLong(lines.get(USE_COUNT_INDEX)));
        component.setLastModified(Instant.ofEpochSecond(Long.parseLong(lines.get(LAST_MODIFIED_INDEX))));
        int endLineNumber = processProperties(component, lines);
        endLineNumber++;
        if (component instanceof Artifact) {
            processArtifact(bucket, lines, endLineNumber, (Artifact) component);
        } else if (component instanceof GameCharacter) {
            processCharacter(bucket, lines, endLineNumber, (GameCharacter) component);
        } else if (component instanceof Link) {
            processLink(bucket, lines, endLineNumber, (Link) component);
        } else if (component instanceof Program) {
            processProgram(bucket, lines, endLineNumber, (Program) component);
        } else if (component instanceof Space) {
            generateSpace(bucket, lines, endLineNumber, (Space) component);
        }
    }

    private void processArtifact(ImportBucket bucket, List<String> lines, int lineNumber, Artifact artifact) {
        artifact.setHome(getComponent(bucket, lines.get(lineNumber)));
        addLink(bucket, artifact, lines.get(lineNumber + 1));
        setOwner(bucket, artifact, lines.get(lineNumber + 2));
        artifact.setValue(Long.parseLong(lines.get(lineNumber + 3)));
    }

    private void processCharacter(ImportBucket bucket, List<String> lines, int lineNumber, GameCharacter character) {
        character.setHome(getComponent(bucket, lines.get(lineNumber)));
        addLink(bucket, character, lines.get(lineNumber + 1));
        character.setWealth(Long.parseLong(lines.get(lineNumber + 2)));
    }

    private void processLink(ImportBucket bucket, List<String> lines, int lineNumber, Link link) {
        int destinationCount = Integer.parseInt(lines.get(lineNumber));
        for (int i = 1; i <= destinationCount; i++) {
            Long destinationId = parseReference(lines.get(lineNumber + i));
            link.getDestinations().add(getComponent(bucket, destinationId));
        }
        setOwner(bucket, link, lines.get(lineNumber + 1 + destinationCount));
    }

    private void processProgram(ImportBucket bucket, List<String> lines, int lineNumber, Program program) {
        setOwner(bucket, program, lines.get(lineNumber));
    }

    private void generateSpace(ImportBucket bucket, List<String> lines, int lineNumber, Space space) {
        Long id = parseReference(lines.get(lineNumber));
        if (id != -1L) {
            space.setDropTo(getComponent(bucket, id));
        }
        addLink(bucket, space, lines.get(lineNumber + 1));
        setOwner(bucket, space, lines.get(lineNumber + 2));
    }

    private void addLink(ImportBucket bucket, LinkSource component, String reference) {
        Long id = parseReference(reference);
        if (id != -1L) {
            component.getLinks().add((Link)getComponent(bucket, id));
        }
    }

    private void setOwner(ImportBucket bucket, Ownable component, String reference) {
        Long id = parseReference(reference);
        if (id == -1L) {
            component.setOwner(getComponent(bucket, 1L));
        } else {
            component.setOwner(getComponent(bucket, id));
        }
    }

    private int processProperties(Component component, List<String> lines) {
        int lineNumber = FIRST_PROPERTY_INDEX;
        String line = lines.get(FIRST_PROPERTY_INDEX);
        while (!"*End*".equals(line)) {
            int firstColonPosition = line.indexOf(":");
            int secondColonPosition = line.indexOf(":", firstColonPosition + 1);
            String path = line.substring(0, firstColonPosition);
            String flags = line.substring(firstColonPosition + 1, secondColonPosition);
            String value = line.substring(secondColonPosition + 1);
            if ("_/de".equals(path)) {
                component.setDescription(value);
            } else {
                addProperty(component, path, flags, value);
            }
            lineNumber++;
            line = lines.get(lineNumber);
        }
        return lineNumber;
    }

    private void addProperty(Component component, String path, String flags, String value) {
        long type = determineType(flags);
        if (type == 2) {
            StringProperty property = new StringProperty();
            property.setValue(value);
            component.getProperties().setProperty(path, property);
        } else if (type == 3) {
            IntegerProperty property = new IntegerProperty();
            property.setValue(Long.parseLong(value));
            component.getProperties().setProperty(path, property);
        } else if (type == 4) {
            LockProperty property = new LockProperty();
            property.setValue(value);
            component.getProperties().setProperty(path, property);
        } else if (type == 5) {
            ReferenceProperty property = new ReferenceProperty();
            property.setValue(Long.parseLong(value));
            component.getProperties().setProperty(path, property);
        }
    }

    private static final int NAME_INDEX = 1;
    private static final int LOCATION_INDEX = 2;
    private static final int CONTENTS_INDEX = 3;
//    private static final int NEXT_INDEX = 4;
//    private static final int FLAGS_INDEX = 5;
    private static final int CREATED_INDEX = 6;
    private static final int LAST_USED_INDEX = 7;
    private static final int USE_COUNT_INDEX = 8;
    private static final int LAST_MODIFIED_INDEX = 9;
//    *Props* = 10
    private static final int FIRST_PROPERTY_INDEX = 11;
}
