package org.ruhlendavis.meta;

import org.apache.commons.lang3.StringUtils;
import org.ruhlendavis.meta.components.Component;

public class ComponentBuilder {
    public Component generateFromGlowData(String data) {
        if (StringUtils.isBlank(data)) {
            return new Component();
        }
        Component component = new Component();
        component.setId(getDatabaseReference(data));
        component.setName(getName(data));
        return component;
    }

    private Long getDatabaseReference(String data) {
        int end = data.indexOf("\n", 0);
        return Long.parseLong(data.substring(1, end));
    }

    private String getName(String data) {
        int start = data.indexOf("\n", 0) + 1;
        int end =  data.indexOf("\n", start);
        return data.substring(start, end);
    }
}
