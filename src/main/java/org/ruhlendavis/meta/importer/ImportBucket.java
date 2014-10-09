package org.ruhlendavis.meta.importer;

import org.ruhlendavis.meta.components.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportBucket {
    String file = "";
    private Map<Long, Component> components = new HashMap<Long, Component>();
    private Map<Long, List<String>> componentLines = new HashMap<>();

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Map<Long, List<String>> getComponentLines() {
        return componentLines;
    }

    public void setComponentLines(Map<Long, List<String>> componentLines) {
        this.componentLines = componentLines;
    }

    public Map<Long, Component> getComponents() {
        return components;
    }

    public void setComponents(Map<Long, Component> components) {
        this.components = components;
    }
}
