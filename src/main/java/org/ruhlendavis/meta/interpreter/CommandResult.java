package org.ruhlendavis.meta.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.ruhlendavis.meta.interpreter.commands.Command;

public class CommandResult {
    CommandStatus status = CommandStatus.OK;
    List<Command> commands = new ArrayList<>();
    private String commandString;
    private String commandArguments;

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public String getCommandString() {
        return commandString;
    }

    public void setCommandString(String commandString) {
        this.commandString = commandString;
    }

    public String getCommandArguments() {
        return commandArguments;
    }

    public void setCommandArguments(String commandArguments) {
        this.commandArguments = commandArguments;
    }
}
