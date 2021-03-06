package org.mumue.mumue.importer.components.properties;

import java.util.HashMap;
import java.util.Map;

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
