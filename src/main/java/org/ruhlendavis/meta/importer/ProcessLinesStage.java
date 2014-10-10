package org.ruhlendavis.meta.importer;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.components.*;
import org.ruhlendavis.meta.components.Character;

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
            if ("*Props*".equals(line)) {
                propertyListStart = lineCount;
            }
            if (propertyListStart > 0 && "*End*".equals(line)) {
                propertyListEnd = lineCount;
                break;
            }
            lineCount++;
        }

        if (component instanceof Space) {
            generateSpace((Space) component, lines, bucket);
        } else if (component instanceof Artifact) {
            generateArtifact((Artifact) component, lines, bucket);
        } else if (component instanceof Link) {
            generateLink((Link) component, propertyListEnd + 1, lines, bucket);
        } else if (component instanceof Character) {
            generateCharacter((Character) component, lines, bucket);
        } else if (component instanceof Program) {
            generateProgram((Program) component, lines, bucket);
        }

        generateComponentFields(lines, component);
    }

    private void generateArtifact(Artifact artifact, List<String> lines, ImportBucket bucket) {
        artifact.setHome(bucket.getComponents().get(translateStringReferenceToLong(lines.get(lines.size() - 4))));
        addLink(artifact, lines.get(lines.size() - 3), bucket);
        artifact.setOwner(bucket.getComponents().get(translateStringReferenceToLong(lines.get(lines.size() - 2))));
        artifact.setValue(translateStringReferenceToLong(lines.get(lines.size() - 1)));
    }

    private void generateCharacter(Character character, List<String> lines, ImportBucket bucket) {
        character.setHome(bucket.getComponents().get(translateStringReferenceToLong(lines.get(lines.size() - 4))));
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
        link.setOwner(bucket.getComponents().get(translateStringReferenceToLong(lines.get(lines.size() - 1))));
    }

    private void generateProgram(Program program, List<String> lines, ImportBucket bucket) {
        program.setOwner(bucket.getComponents().get(translateStringReferenceToLong(lines.get(lines.size() - 1))));
    }

    private void generateSpace(Space space, List<String> lines, ImportBucket bucket) {
        space.setDropTo(bucket.getComponents().get(translateStringReferenceToLong(lines.get(lines.size() - 3))));
        addLink(space, lines.get(lines.size() - 2), bucket);
        space.setOwner(bucket.getComponents().get(translateStringReferenceToLong(lines.get(lines.size() - 1))));
    }

    private long translateStringReferenceToLong(String databaseReference) {
        if (StringUtils.isBlank(databaseReference)) {
            return GlobalConstants.REFERENCE_UNKNOWN;
        }
        return Long.parseLong(databaseReference.replace("#", ""));
    }

    private void generateComponentFields(List<String> lines, Component component) {
        component.setName(lines.get(1));
        component.setCreated(Instant.ofEpochSecond(translateStringReferenceToLong(lines.get(6))));
        component.setLastUsed(Instant.ofEpochSecond(translateStringReferenceToLong(lines.get(7))));
        component.setUseCount(translateStringReferenceToLong(lines.get(8)));
        component.setLastModified(Instant.ofEpochSecond(translateStringReferenceToLong(lines.get(9))));
        for (String line : lines) {
            if (line.startsWith("_/de")) {
                component.setDescription(extractDescription(line));
            }
        }
    }

    private String extractDescription(String line) {
        int position = line.indexOf(":") + 1;
        position = line.indexOf(":", position) + 1;
        return line.substring(position);
    }
}
