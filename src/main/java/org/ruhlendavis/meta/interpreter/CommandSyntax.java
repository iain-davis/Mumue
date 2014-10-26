package org.ruhlendavis.meta.interpreter;

import org.ruhlendavis.meta.interpreter.commands.Command;

public class CommandSyntax {
    private boolean token;
    private Command command;

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
