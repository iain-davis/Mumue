package org.ruhlendavis.meta;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.components.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComponentBuilder {
    public Component generateFromGlowData(String data) {
        if (StringUtils.isBlank(data)) {
            return new Component();
        }
        Component component = new Component();
        List<String> lines = new ArrayList<>(Arrays.asList(data.split("\n")));
        component.setId(Long.parseLong(lines.get(0).substring(1)));
        component.setName(lines.get(1));
        lines.remove(lines.get(0));
        lines.remove(lines.get(0));
        return component;
    }
}
