package org.mumue.mumue.interpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class CommandListBuilderTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    private final Collection<CommandEntry> commandEntries = new ArrayList<>();

    @Mock CommandSyntaxBuilder commandSyntaxBuilder;
    @Mock CommandEntryDao dao;
    @InjectMocks CommandListBuilder builder;

    @Before
    public void beforeEach() {
        when(dao.getCommands()).thenReturn(commandEntries);
    }

    @Test
    public void buildNeverReturnsNull() {
        assertNotNull(builder.build());
    }

    @Test
    public void buildPutsSyntaxForOneEntryOnList() {
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
