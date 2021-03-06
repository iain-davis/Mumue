package org.mumue.mumue.interpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.interpreter.commands.CommandSay;

public class CommandSyntaxBuilderTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));

    private final CommandEntry entry = new CommandEntry();
    private final CommandSyntaxBuilder builder = new CommandSyntaxBuilder(new CommandNameMapProvider(injector));

    @Before
    public void beforeEach() {
        entry.setCommandIdentifier("say");
    }

    @Test
    public void buildNeverReturnsNull() {
        assertNotNull(builder.build(entry));
    }

    @Test
    public void buildUsesEntryDisplay() {
        entry.setDisplay(RandomStringUtils.randomAlphabetic(17));
        CommandSyntax syntax = builder.build(entry);
        assertThat(syntax.getDisplay(), equalTo(entry.getDisplay()));
    }

    @Test
    public void buildUsesEntryIsToken() {
        entry.setToken(true);
        CommandSyntax syntax = builder.build(entry);
        assertTrue(syntax.isToken());
    }

    @Test
    public void buildSetsCommand() {
        CommandSyntax syntax = builder.build(entry);

        assertThat(syntax.getCommand(), instanceOf(CommandSay.class));
    }

    @Test
    public void buildThrowExceptionOnUnknownCommandClass() {
        entry.setCommandIdentifier(RandomStringUtils.randomAlphabetic(18));

        thrown.expect(UnknownCommandIdentifierException.class);
        thrown.expectMessage("Unknown command identifier '" + entry.getCommandIdentifier() + "'");

        builder.build(entry);
    }
}
