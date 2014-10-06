package org.ruhlendavis.meta;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.components.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentBuilder {
    public Component generate(String data) {
        if (StringUtils.isBlank(data)) {
            return new Component();
        }
        Component component = new Component();
        List<String> lines = new ArrayList<>(Arrays.asList(data.split("\n")));
        component.setId(Long.parseLong(lines.get(0).substring(1)));
        component.setName(lines.get(1));
        component.setCreatedTimeStamp(Long.parseLong(lines.get(6)));
        component.setUsedTimeStamp(Long.parseLong(lines.get(7)));
        component.setUseCount(Long.parseLong(lines.get(8)));
        component.setModifiedTimeStamp(Long.parseLong(lines.get(9)));
        for (String line : lines) {
            if (line.startsWith("_/de")) {
                component.setDescription(extractDescription(line));
            }
        }
        return component;
    }

    private String extractDescription(String line) {
        int position = line.indexOf(":") + 1;
        position = line.indexOf(":", position) + 1;
        return line.substring(position);
    }
}
