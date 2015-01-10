package org.ruhlendavis.meta.importer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ruhlendavis.meta.components.Component;
import org.ruhlendavis.meta.player.Player;

public class ImportBucket {
    private String file = "";
    private Map<Long, Component> components = new HashMap<>();
    private Map<Long, List<String>> componentLines = new HashMap<>();
    private List<String> sourceLines = new ArrayList<>();
    private List<String> parameterLines = new ArrayList<>();
    private Long databaseItemCount = 0L;
    private Boolean failed = false;
    private int parameterCount = 0;
    private List<Player> players = new ArrayList<>();

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Map<Long, List<String>> getComponentLines() {
        return componentLines;
    }

    public Map<Long, Component> getComponents() {
        return components;
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

    public int getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(int parameterCount) {
        this.parameterCount = parameterCount;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
