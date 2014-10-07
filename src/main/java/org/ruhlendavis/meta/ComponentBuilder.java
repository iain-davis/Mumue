package org.ruhlendavis.meta;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.components.*;

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

        BitSet flags = getFlags(lines.get(5).split(" ")[0]);
        if (flags.get(0)) {
            component = new Space();
            generateComponentFields(lines, component);
            ((Space) component).setDropTo(Long.parseLong(lines.get(lines.size() - 3)));
            component.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 1)));
        } else if (flags.get(1)) {
            component = new Artifact();
            generateComponentFields(lines, component);
            component.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 1)));
        } else if (flags.get(2)) {
            component = new Link();
            generateComponentFields(lines, component);
            component.setOwnerId(translateStringReferenceToLong(lines.get(lines.size() - 1)));
//        } else if (flags.get(3)) {
//            // Player
//        } else if (flags.get(4)) {
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
    private BitSet getFlags(String s) {
        Long l = Long.parseLong(s);
        return BitSet.valueOf(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(l).array());
    }
}
