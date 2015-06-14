package org.mumue.mumue.interpreter;

public class CommandEntry {
    private long id;
    private String display;
    private String minimumPartial;
    private String commandIdentifier;
    private boolean token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getMinimumPartial() {
        return minimumPartial;
    }

    public void setMinimumPartial(String minimumPartial) {
        this.minimumPartial = minimumPartial;
    }

    public String getCommandIdentifier() {
        return commandIdentifier;
    }

    public void setCommandIdentifier(String commandIdentifier) {
        this.commandIdentifier = commandIdentifier;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }
}
