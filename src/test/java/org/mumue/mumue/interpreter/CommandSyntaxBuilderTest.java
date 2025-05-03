package org.mumue.mumue.interpreter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.interpreter.commands.CommandSay;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThrows;

class CommandSyntaxBuilderTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));

    private final CommandEntry entry = new CommandEntry();
    private final CommandSyntaxBuilder builder = new CommandSyntaxBuilder(new CommandNameMapProvider(injector));

    @BeforeEach
    void beforeEach() {
        entry.setCommandIdentifier("say");
    }

    @Test
    void buildNeverReturnsNull() {
        CommandSyntax syntax = builder.build(entry);

        assertThat(syntax, notNullValue());
    }

    @Test
    void buildUsesEntryDisplay() {
        entry.setDisplay(RandomStringUtils.insecure().nextAlphabetic(17));
        CommandSyntax syntax = builder.build(entry);
        assertThat(syntax.getDisplay(), equalTo(entry.getDisplay()));
    }

    @Test
    void buildUsesEntryIsToken() {
        entry.setToken(true);
        CommandSyntax syntax = builder.build(entry);

        assertThat(syntax.isToken(), equalTo(true));
    }

    @Test
    void buildSetsCommand() {
        CommandSyntax syntax = builder.build(entry);

        assertThat(syntax.getCommand(), instanceOf(CommandSay.class));
    }

    @Test
    void buildThrowExceptionOnUnknownCommandClass() {
        entry.setCommandIdentifier(RandomStringUtils.insecure().nextAlphabetic(18));

        Exception exception = assertThrows(UnknownCommandIdentifierException.class, () -> builder.build(entry));

        assertThat(exception.getMessage(), equalTo("Unknown command identifier '" + entry.getCommandIdentifier() + "'"));
    }
}
