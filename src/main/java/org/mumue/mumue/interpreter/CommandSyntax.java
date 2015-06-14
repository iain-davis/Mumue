package org.mumue.mumue.interpreter;

import org.mumue.mumue.interpreter.commands.Command;

class CommandSyntax {
    private String display;
    private Command command;
    private boolean token;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }
}
