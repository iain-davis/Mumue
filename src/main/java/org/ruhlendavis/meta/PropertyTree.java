package org.ruhlendavis.meta;

import java.util.HashMap;
import java.util.Map;
import org.ruhlendavis.meta.properties.Property;

public class PropertyTree {
    private Map<String, Property> properties = new HashMap<>();

    public Property getProperty(String path) {
        return properties.get(path);
    }

    public void setProperty(String path, Property property) {
        properties.put(path, property);
    }

    public int size() {
        return properties.size();
    }
}
