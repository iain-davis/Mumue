package org.ruhlendavis.mumue.interpreter;

import org.ruhlendavis.mumue.interpreter.commands.Command;

public class CommandEntry {
    private long id;
    private String display;
    private String minimumPartial;
    private String commandClass;
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

    public String getCommandClass() {
        return commandClass;
    }

    public void setCommandClass(String commandClass) {
        this.commandClass = commandClass;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public Command getCommand() {
        return null;
    }

    public void setCommand(Command command) {

    }
}
