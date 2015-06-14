package org.mumue.mumue.interpreter;

import org.mumue.mumue.interpreter.commands.Command;

public class CommandSyntaxBuilder {
    private CommandNameMapProvider provider = new CommandNameMapProvider();

    public CommandSyntax build(CommandEntry entry) {
        CommandSyntax syntax = new CommandSyntax();
        syntax.setDisplay(entry.getDisplay());
        syntax.setToken(entry.isToken());
        Command command = provider.get().get(entry.getCommandIdentifier());
        if (command == null) {
            throw new UnknownCommandIdentifierException("Unknown command identifier '" + entry.getCommandIdentifier() + "'");
        }
        syntax.setCommand(command);
        return syntax;
    }
}
