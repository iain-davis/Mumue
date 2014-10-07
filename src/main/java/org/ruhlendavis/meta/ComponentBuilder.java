package org.ruhlendavis.meta;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.components.Artifact;
import org.ruhlendavis.meta.components.Component;
import org.ruhlendavis.meta.components.Space;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class ComponentBuilder {
    public Component generate(String data) {
        if (StringUtils.isBlank(data)) {
            return new Component();
        }

        Component component = new Component();
        List<String> lines = new ArrayList<>(Arrays.asList(data.split("\n")));

        BitSet flags = getFlags(lines.get(5).split(" ")[0]);
        if (flags.get(0)) {
            component = new Space();
        } else if (flags.get(1)) {
            component = new Artifact();
        }
//        } else if (flags.get(2)) {
//            // Exit
//        } else if (flags.get(3)) {
//            // Player
//        } else if (flags.get(4)) {
//            // Player
//        } else if (flags.get(6) || flags.get(7)) {
//            // Garbage
//        }
        generateComponentFields(lines, component);
        return component;
    }

    private BitSet getFlags(String s) {
        Long l = Long.parseLong(s);
        return BitSet.valueOf(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(l).array());
    }

    private void generateComponentFields(List<String> lines, Component component) {
        component.setId(Long.parseLong(lines.get(0).substring(1)));
        component.setName(lines.get(1));
        component.setCreated(Instant.ofEpochSecond(Long.parseLong(lines.get(6))));
        component.setLastUsed(Instant.ofEpochSecond(Long.parseLong(lines.get(7))));
        component.setUseCount(Long.parseLong(lines.get(8)));
        component.setLastModified(Instant.ofEpochSecond(Long.parseLong(lines.get(9))));
        component.setOwnerId(Long.parseLong(lines.get(lines.size() - 1)));
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
