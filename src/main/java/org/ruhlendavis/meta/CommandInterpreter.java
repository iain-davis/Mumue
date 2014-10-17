package org.ruhlendavis.meta;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.ruhlendavis.meta.commands.Command;
import org.ruhlendavis.meta.commands.CommandImport;
import org.ruhlendavis.meta.commands.CommandPose;
import org.ruhlendavis.meta.commands.CommandSay;
import org.ruhlendavis.meta.commands.CommandSayDirected;

public class CommandInterpreter {
    private static final ConcurrentMap<String, Command> tokenCommandList = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Command> commandList = new ConcurrentHashMap<>();

    static {
        putTokenCommand("\"", new CommandSay().withFullCommand("\""));
        putTokenCommand("'", new CommandSay().withFullCommand("'"));
        putTokenCommand(":", new CommandPose().withFullCommand(":"));
        putTokenCommand(";", new CommandPose().withFullCommand(";"));
        putTokenCommand("`", new CommandSayDirected().withFullCommand("`"));
        putCommand("@import", new CommandImport().withFullCommand("@import"));
        putCommand("p", new CommandPose().withFullCommand("pose"));
        putCommand("s", new CommandSay().withFullCommand("say"));
    }

    public CommandResult interpret(String line) {
        CommandResult result = new CommandResult();

        for (Entry<String, Command> entry : tokenCommandList.entrySet()) {
            if (entry.getKey().equals(line.toLowerCase().substring(0,1))) {
                result.setStatus(CommandStatus.OK);
                result.getCommands().add(entry.getValue());
            }
        }

        result.setStatus(CommandStatus.OK);
        for (Entry<String, Command> entry : commandList.entrySet()) {
            if (line.toLowerCase().startsWith(entry.getKey())) {
                if (result.getCommands().size() == 1) {
                    result.setStatus(CommandStatus.AMBIGUOUS_COMMAND);
                }
                result.getCommands().add(entry.getValue());
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

    public static void putTokenCommand(String token, Command command) {
        tokenCommandList.put(token.toLowerCase(), command);
    }

    public static void putCommand(String minimumPartial, Command command) {
        commandList.put(minimumPartial.toLowerCase(), command);
    }

    public static void clearCommands() {
        tokenCommandList.clear();
        commandList.clear();
    }
}
