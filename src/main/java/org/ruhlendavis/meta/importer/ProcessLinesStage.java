package org.ruhlendavis.meta.importer;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.components.*;
import org.ruhlendavis.meta.components.Character;
import org.ruhlendavis.meta.properties.IntegerProperty;
import org.ruhlendavis.meta.properties.Property;
import org.ruhlendavis.meta.properties.StringProperty;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ProcessLinesStage implements ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        for (Map.Entry<Long, List<String>> entry : bucket.getComponentLines().entrySet()) {
            processComponent(bucket, bucket.getComponents().get(entry.getKey()), entry.getValue());
        }
    }

    private void processComponent(ImportBucket bucket, Component component, List<String> lines) {
        int propertyListStart = -1;
        int propertyListEnd = -1;
        int lineCount = 0;
        for (String line : lines) {
            lineCount++;
            if ("*Props*".equals (line)) {
                propertyListStart = lineCount;
                continue;
            }
            if (propertyListStart > 0) {
                if ("*End*".equals(line)) {
                    propertyListEnd = lineCount;
                    break;
                }
                String[] parts = line.split(":");
                if ("_/de".equals(parts[0])) {
                    component.setDescription(extractDescription(line));
                } else {
                    addProperty(component, parts);
                }
            }
        }

        if (component instanceof Space) {
            generateSpace((Space) component, lines, bucket);
        } else if (component instanceof Artifact) {
            generateArtifact((Artifact) component, lines, bucket);
        } else if (component instanceof Link) {
            generateLink((Link) component, propertyListEnd, lines, bucket);
        } else if (component instanceof Character) {
            generateCharacter((Character) component, lines, bucket);
        } else if (component instanceof Program) {
            generateProgram((Program) component, lines, bucket);
        }

        generateComponentFields(lines, component, bucket);
    }

    private void addProperty(Component component, String[] parts) {
        long type = Long.parseLong(parts[1]) & 0x7;
        if (type == 2) {
            StringProperty property = new StringProperty();
            property.setValue(parts[2]);
            component.getProperties().setProperty(parts[0], property);
        } else if (type == 3) {
            IntegerProperty property = new IntegerProperty();
            property.setValue(Long.parseLong(parts[2]));
            component.getProperties().setProperty(parts[0], property);
        }
    }

    private void generateArtifact(Artifact artifact, List<String> lines, ImportBucket bucket) {
        artifact.setHome(getComponent(bucket, lines.get(lines.size() - 4)));
        addLink(artifact, lines.get(lines.size() - 3), bucket);
        artifact.setOwner(getComponent(bucket, lines.get(lines.size() - 2)));
        artifact.setValue(translateStringReferenceToLong(lines.get(lines.size() - 1)));
    }

    private void generateCharacter(Character character, List<String> lines, ImportBucket bucket) {
        character.setHome(getComponent(bucket, lines.get(lines.size() - 4)));
        addLink(character, lines.get(lines.size() - 3), bucket);
        character.setWealth(translateStringReferenceToLong(lines.get(lines.size() - 2)));
        character.setPassword(lines.get(lines.size() - 1));
    }

    private void addLink(LinkSource component, String linkReference, ImportBucket bucket) {
        Long id = translateStringReferenceToLong(linkReference);
        if (id != -1) {
            component.getLinks().add((Link)bucket.getComponents().get(id));
        }
    }

    private void generateLink(Link link, int destinationCountPosition, List<String> lines, ImportBucket bucket) {
        Long destinationCount = translateStringReferenceToLong(lines.get(destinationCountPosition));
        for (int i = 0; i < destinationCount; i++) {
            Long destinationId = translateStringReferenceToLong(lines.get(destinationCountPosition + 1 + i));
            link.getDestinations().add(bucket.getComponents().get(destinationId));
        }
        link.setOwner(getComponent(bucket, lines.get(lines.size() - 1)));
    }

    private void generateProgram(Program program, List<String> lines, ImportBucket bucket) {
        program.setOwner(getComponent(bucket, lines.get(lines.size() - 1)));
    }

    private void generateSpace(Space space, List<String> lines, ImportBucket bucket) {
        space.setDropTo(getComponent(bucket, lines.get(lines.size() - 3)));
        addLink(space, lines.get(lines.size() - 2), bucket);
        space.setOwner(getComponent(bucket, lines.get(lines.size() - 1)));
    }

    private long translateStringReferenceToLong(String databaseReference) {
        if (StringUtils.isBlank(databaseReference)) {
            return GlobalConstants.REFERENCE_UNKNOWN;
        }
        return Long.parseLong(databaseReference.replace("#", ""));
    }

    private void generateComponentFields(List<String> lines, Component component, ImportBucket bucket) {
        component.setName(lines.get(1));
        component.setLocation(getComponent(bucket, lines.get(2)));
        component.getContents().add(getComponent(bucket, lines.get(3)));
        component.setCreated(Instant.ofEpochSecond(translateStringReferenceToLong(lines.get(6))));
        component.setLastUsed(Instant.ofEpochSecond(translateStringReferenceToLong(lines.get(7))));
        component.setUseCount(translateStringReferenceToLong(lines.get(8)));
        component.setLastModified(Instant.ofEpochSecond(translateStringReferenceToLong(lines.get(9))));
    }

    private Component getComponent(ImportBucket bucket, String line) {
        return bucket.getComponents().get(translateStringReferenceToLong(line));
    }

    private String extractDescription(String line) {
        int position = line.indexOf(":") + 1;
        position = line.indexOf(":", position) + 1;
        return line.substring(position);
    }
}
