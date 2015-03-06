package org.ruhlendavis.mumue.interpreter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandListBuilder {
    private CommandSyntaxBuilder syntaxBuilder = new CommandSyntaxBuilder();
    private CommandEntryDao dao = new CommandEntryDao();

    public Map<String, CommandSyntax> build() {
        Collection<CommandEntry> commandEntries = dao.getCommands();
        Map<String, CommandSyntax> commandList = new HashMap<>();

        for (CommandEntry entry : commandEntries) {
            commandList.put(entry.getMinimumPartial(), syntaxBuilder.build(entry));
        }

        return commandList;
    }
}
