package org.ruhlendavis.meta;

import java.util.ArrayList;
import java.util.List;
import org.ruhlendavis.meta.commands.Command;

public class CommandResult {
    CommandStatus status = CommandStatus.OK;
    List<Command> commands = new ArrayList<>();

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public CommandResult withStatus(CommandStatus status) {
        this.status = status;
        return this;
    }

    public CommandResult withCommand(Command command) {
        this.commands.add(command);
        return this;
    }
}
