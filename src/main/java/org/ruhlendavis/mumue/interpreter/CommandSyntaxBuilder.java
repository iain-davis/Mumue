package org.ruhlendavis.mumue.interpreter;

import org.ruhlendavis.mumue.interpreter.commands.Command;

public class CommandSyntaxBuilder {
    private CommandNameMapProvider provider = new CommandNameMapProvider();

    public CommandSyntax build(CommandEntry entry) {
        CommandSyntax syntax = new CommandSyntax();
        syntax.setDisplay(entry.getDisplay());
        syntax.setToken(entry.isToken());
        Command command = provider.get().get(entry.getCommandClass());
        if (command == null) {
            throw new UnknownCommandClassException("Unknown command class '" + entry.getCommandClass() + "'");
        }
        syntax.setCommand(command);
        return syntax;
    }
}
