package org.ruhlendavis.meta;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.components.*;
import org.ruhlendavis.meta.components.Character;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentBuilder {
    private Map<Long, Link> links = new HashMap<>();
    public Component generate(List<String> lines) {
        if (lines.isEmpty()) {
            return new Garbage();
        }

        Component component;
        long id = translateStringReferenceToLong(lines.get(0));
        long type = Long.parseLong(lines.get(5).split(" ")[0]) & 0x7;
        if (type == 0) {
            component = generateSpace(lines);
        } else if (type == 1) {
            component = generateArtifact(lines);
        } else if (type == 2) {
            component = generateLink(id, lines);
        } else if (type == 3) {
            component = generateCharacter(lines);
        } else if (type == 4) {
            component = generateProgram(lines);
        } else {
            component = new Garbage();
        }
        component.setId(id);
        generateComponentFields(lines, component);
        return component;
    }

    private Component generateArtifact(List<String> lines) {
        Artifact artifact = new Artifact();
        artifact.setHome(translateStringReferenceToLong(lines.get(lines.size() - 4)));
        String linkReference = lines.get(lines.size() - 3);
        addLink(artifact, linkReference);
        artifact.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 2)));
        artifact.setValue(translateStringReferenceToLong(lines.get(lines.size() - 1)));
        return artifact;
    }

    private void addLink(Artifact artifact, String linkReference) {
        Long id = translateStringReferenceToLong(linkReference);
        if (id != -1) {
            artifact.getLinks().add(getOrCreateLink(id));
        }
    }

    private Component generateCharacter(List<String> lines) {
        Character character = new Character();
        character.setHome(translateStringReferenceToLong(lines.get(lines.size() - 4)));
        character.setWealth(translateStringReferenceToLong(lines.get(lines.size() - 2)));
        character.setPassword(lines.get(lines.size() - 1));
        return character;
    }

    private Component generateLink(Long id, List<String> lines) {
        Link link = getOrCreateLink(id);
        link.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 1)));
        return link;
    }

    private Component generateProgram(List<String> lines) {
        Program program = new Program();
        program.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 1)));
        return program;
    }

    private Component generateSpace(List<String> lines) {
        Space space = new Space();
        space.setDropTo(Long.parseLong(lines.get(lines.size() - 3)));
        space.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 1)));
        return space;
    }

    private Link getOrCreateLink(Long id) {
        if (links.containsKey(id)) {
            return links.get(id);
        }
        Link link = new Link();
        link.setId(id);
        return link;
    }

    private long translateStringReferenceToLong(String databaseReference) {
        if (StringUtils.isBlank(databaseReference)) {
            return 0L;
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
