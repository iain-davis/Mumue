package org.ruhlendavis.meta.importer;

import org.ruhlendavis.meta.components.Component;

import java.util.*;

public class ImportBucket {
    private String file = "";
    private Map<Long, Component> components = new HashMap<>();
    private Map<Long, List<String>> componentLines = new HashMap<>();
    private List<String> sourceLines = new ArrayList<>();
    private List<String> parameterLines = new ArrayList<>();
    private Long databaseItemCount = new Long(0L);
    private Boolean failed = false;
    private Long parameterCount = new Long(0L);

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

    public void setSourceLines(List<String> sourceLines) {
        this.sourceLines = sourceLines;
    }

    public List<String> getSourceLines() {
        return sourceLines;
    }

    public List<String> getParameterLines() {
        return parameterLines;
    }

    public void setParameterLines(List<String> parameterLines) {
        this.parameterLines = parameterLines;
    }

    public Long getDatabaseItemCount() {
        return databaseItemCount;
    }

    public void setDatabaseItemCount(Long databaseItemCount) {
        this.databaseItemCount = databaseItemCount;
    }

    public Boolean isFailed() {
        return failed;
    }

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    public Long getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(Long parameterCount) {
        this.parameterCount = parameterCount;
    }
}
