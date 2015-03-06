package org.ruhlendavis.mumue.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.ruhlendavis.mumue.interpreter.commands.Command;
import org.ruhlendavis.mumue.interpreter.commands.CommandSay;

public class CommandNameMapProvider {
    private static final Map<String, Command> commandNameMap = new HashMap<>();
    public Map<String, Command> get() {
        return commandNameMap;
    }

    static {
        commandNameMap.put("say", new CommandSay());
    }
}
