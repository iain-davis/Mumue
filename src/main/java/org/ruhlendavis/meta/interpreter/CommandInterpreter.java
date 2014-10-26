package org.ruhlendavis.meta.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.ruhlendavis.meta.interpreter.commands.Command;

public class CommandInterpreter {
    private Map<String, CommandSyntax> commands = new HashMap<>();

    public CommandResult interpret(String line) {
        CommandResult result = new CommandResult();

        for (Entry<String, CommandSyntax> entry : commands.entrySet()) {
            if (line.toLowerCase().startsWith(entry.getKey().toLowerCase())) {
                result.setStatus(CommandStatus.OK);
                if (entry.getValue().isToken()) {
                    extractTokenArguments(line, result);
                } else {
                    extractNormalArguments(line, result);
                }
                result.getCommands().add(entry.getValue().getCommand());
            }
        }

        if (result.getCommands().size() == 0) {
            result.setStatus(CommandStatus.UNKNOWN_COMMAND);
        } else if (result.getCommands().size() == 1) {
            result.setStatus(CommandStatus.OK);
        } else {
            result.setStatus(CommandStatus.AMBIGUOUS_COMMAND);
        }

        return result;
    }

    private void extractTokenArguments(String line, CommandResult result) {
        result.setCommandString(line.substring(0, 1));
        result.setCommandArguments(line.substring(1));
    }

    private void extractNormalArguments(String line, CommandResult result) {
        if (line.contains(" ")) {
            result.setCommandString(line.substring(0, line.indexOf(" ")));
            result.setCommandArguments(line.substring(line.indexOf(" ") + 1));
        } else {
            result.setCommandString(line);
        }
    }

    public Map<String, CommandSyntax> getCommands() {
        return commands;
    }

    public void putCommand(String minimumPartial, Command command) {
        putCommand(minimumPartial, command, false);
    }

    public void putTokenCommand(String token, Command command) {
        putCommand(token, command, true);
    }

    private void putCommand(String minimumPartial, Command command, boolean isToken) {
        CommandSyntax syntax = new CommandSyntax();
        syntax.setCommand(command);
        syntax.setToken(isToken);
        commands.put(minimumPartial, syntax);
    }
}
