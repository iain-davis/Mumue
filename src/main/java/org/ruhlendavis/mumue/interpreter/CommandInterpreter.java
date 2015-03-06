package org.ruhlendavis.mumue.interpreter;

import java.util.Map;
import java.util.Map.Entry;

public class CommandInterpreter {
    private CommandListBuilder commandListBuilder = new CommandListBuilder();

    public CommandResult interpret(String text) {
        Map<String, CommandSyntax> commandList = commandListBuilder.build();
        CommandResult result = new CommandResult();

        for (Entry<String, CommandSyntax> entry : commandList.entrySet()) {
            if (commandMatches(text, entry)) {
                result.getCommands().add(entry.getValue().getCommand());
                if (entry.getValue().isToken()) {
                    separateCommandAndArgumentsForToken(text, result);
                } else {
                    separateCommandAndArguments(text, result);
                }
            }
        }

        setResultStatus(result, result.getCommands().size());

        return result;
    }

    private void setResultStatus(CommandResult result, int size) {
        if (size == 0) {
            result.setStatus(CommandStatus.UNKNOWN_COMMAND);
        } else if (size == 1) {
            result.setStatus(CommandStatus.OK);
        } else {
            result.setStatus(CommandStatus.AMBIGUOUS_COMMAND);
        }
    }

    private boolean commandMatches(String text, Entry<String, CommandSyntax> entry) {
        return text.toLowerCase().startsWith(entry.getKey().toLowerCase());
    }

    private void separateCommandAndArguments(String line, CommandResult result) {
        if (line.contains(" ")) {
            result.setCommandString(line.substring(0, line.indexOf(" ")));
            result.setCommandArguments(line.substring(line.indexOf(" ") + 1));
        } else {
            result.setCommandString(line);
        }
    }

    private void separateCommandAndArgumentsForToken(String line, CommandResult result) {
        result.setCommandString(line.substring(0, 1));
        result.setCommandArguments(line.substring(1));
    }
}
