package org.ruhlendavis.mumue.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.ruhlendavis.mumue.interpreter.commands.Command;

public class CommandResult {
    CommandStatus status = CommandStatus.UNKNOWN_COMMAND;
    List<String> commands = new ArrayList<>();
    private String commandString;
    private String commandArguments;
    private Command command;

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public List<String> getCommands() {
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

    public void setCommand(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
