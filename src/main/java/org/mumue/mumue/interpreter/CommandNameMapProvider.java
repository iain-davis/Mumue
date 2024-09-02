package org.mumue.mumue.interpreter;

import java.util.HashMap;
import java.util.Map;
import jakarta.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.interpreter.commands.Command;
import org.mumue.mumue.interpreter.commands.CommandPose;
import org.mumue.mumue.interpreter.commands.CommandSay;
import org.mumue.mumue.interpreter.commands.CommandSayDirected;

public class CommandNameMapProvider {
    private static Map<String, Command> commandNameMap;
    private final Injector injector;

    @Inject
    public CommandNameMapProvider(Injector injector) {
        this.injector = injector;
    }

    public Map<String, Command> get() {
        if (commandNameMap == null) {
            buildMap();
        }
        return commandNameMap;
    }

    private synchronized void buildMap() {
        commandNameMap = new HashMap<>();
        commandNameMap.put("say", injector.getInstance(CommandSay.class));
        commandNameMap.put("pose", injector.getInstance(CommandPose.class));
        commandNameMap.put("directed say", injector.getInstance(CommandSayDirected.class));
    }
}
