package org.mumue.mumue.interpreter;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandListBuilderTest {
    private final Collection<CommandEntry> commandEntries = new ArrayList<>();

    private final CommandSyntaxBuilder commandSyntaxBuilder = mock(CommandSyntaxBuilder.class);
    private final CommandEntryDao dao = mock(CommandEntryDao.class);
    private final CommandListBuilder builder = new CommandListBuilder(commandSyntaxBuilder, dao);

    @BeforeEach
    void beforeEach() {
        when(dao.getCommands()).thenReturn(commandEntries);
    }

    @Test
    void buildNeverReturnsNull() {
        assertNotNull(builder.build());
    }

    @Test
    void buildPutsSyntaxForOneEntryOnList() {
        String minimumPartial = RandomStringUtils.insecure().nextAlphabetic(17);
        CommandSyntax syntax = new CommandSyntax();
        CommandEntry entry = new CommandEntry();
        entry.setMinimumPartial(minimumPartial);
        commandEntries.add(entry);
        when(commandSyntaxBuilder.build(entry)).thenReturn(syntax);

        Map<String, CommandSyntax> commandList = builder.build();

        assertThat(commandList.size(), equalTo(1));
        CommandSyntax expected = commandList.get(minimumPartial);
        assertThat(expected, sameInstance(syntax));
    }
}
