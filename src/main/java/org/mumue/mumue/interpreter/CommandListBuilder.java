package org.mumue.mumue.interpreter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

public class CommandListBuilder {
    private final CommandSyntaxBuilder syntaxBuilder;
    private final CommandEntryDao dao;

    @Inject
    public CommandListBuilder(CommandSyntaxBuilder syntaxBuilder, CommandEntryDao dao) {
        this.syntaxBuilder = syntaxBuilder;
        this.dao = dao;
    }

    public Map<String, CommandSyntax> build() {
        Collection<CommandEntry> commandEntries = dao.getCommands();
        Map<String, CommandSyntax> commandList = new HashMap<>();

        for (CommandEntry entry : commandEntries) {
            commandList.put(entry.getMinimumPartial(), syntaxBuilder.build(entry));
        }

        return commandList;
    }
}
