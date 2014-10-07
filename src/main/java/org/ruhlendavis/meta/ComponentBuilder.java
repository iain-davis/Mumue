package org.ruhlendavis.meta;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.components.*;
import org.ruhlendavis.meta.components.Character;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;
import java.util.BitSet;
import java.util.List;

public class ComponentBuilder {
    public Component generate(List<String> lines) {
        if (lines.isEmpty()) {
            return new Component();
        }
        Component component = new Garbage();
        long type = Long.parseLong(lines.get(5).split(" ")[0]) & 0x7;
        if (type == 0) {
            component = new Space();
            generateComponentFields(lines, component);
            ((Space) component).setDropTo(Long.parseLong(lines.get(lines.size() - 3)));
            component.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 1)));
        } else if (type == 1) {
            component = new Artifact();
            generateComponentFields(lines, component);
            component.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 1)));
        } else if (type == 2) {
            component = new Link();
            generateComponentFields(lines, component);
            component.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 1)));
        } else if (type == 3) {
            component = new Character();
            generateComponentFields(lines, component);
//        } else if (type == 4) {
//            // Program
        }

        return component;
    }

    private long translateStringReferenceToLong(String databaseReference) {
        if (StringUtils.isBlank(databaseReference)) {
            return 0L;
        }
        return Long.parseLong(databaseReference.replace("#", ""));
    }

    private void generateComponentFields(List<String> lines, Component component) {
        component.setId(translateStringReferenceToLong(lines.get(0)));
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
