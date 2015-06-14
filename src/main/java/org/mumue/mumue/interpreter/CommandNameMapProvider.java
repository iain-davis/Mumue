package org.mumue.mumue.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.mumue.mumue.interpreter.commands.Command;
import org.mumue.mumue.interpreter.commands.CommandPose;
import org.mumue.mumue.interpreter.commands.CommandSay;
import org.mumue.mumue.interpreter.commands.CommandSayDirected;

public class CommandNameMapProvider {
    private static final Map<String, Command> commandNameMap = new HashMap<>();
    public Map<String, Command> get() {
        return commandNameMap;
    }

    static {
        commandNameMap.put("say", new CommandSay());
        commandNameMap.put("pose", new CommandPose());
        commandNameMap.put("directed say", new CommandSayDirected());
    }
}
