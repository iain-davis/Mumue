package org.ruhlendavis.meta;

import org.ruhlendavis.meta.properties.Property;

import java.util.HashMap;
import java.util.Map;

public class PropertyTree {
    private Map<String, Property> properties = new HashMap<>();

    public Property getProperty(String path) {
        Property property = properties.get(path);
        if (property == null) {
            property = new Property();
        }
        return property;
    }

    public void setProperty(String path, Property property) {
        properties.put(path, property);
    }
}
