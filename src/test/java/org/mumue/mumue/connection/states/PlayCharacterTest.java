package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.interpreter.CommandInterpreter;
import org.mumue.mumue.interpreter.CommandResult;
import org.mumue.mumue.interpreter.CommandStatus;
import org.mumue.mumue.interpreter.commands.Command;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PlayCharacterTest {
    private final Command command = mock(Command.class);
    private final CommandResult result = mock(CommandResult.class);
    private final CommandInterpreter commandInterpreter = mock(CommandInterpreter.class);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final GameCharacter character = new GameCharacter();
    private final Connection connection = new Connection(configuration).withPlayer(Nimue.player().build()).withCharacter(character);
    private final PlayCharacter playCharacter = new PlayCharacter(commandInterpreter, textMaker);

    @Before
    public void beforeEach() {
        when(result.getStatus()).thenReturn(CommandStatus.OK);
        when(commandInterpreter.interpret(anyString())).thenReturn(result);
        when(result.getCommand()).thenReturn(command);
    }

    @Test
    public void returnPlayCharacterStageWithoutInput() {
        ConnectionState next = playCharacter.execute(connection, configuration);

        assertThat(next, sameInstance(playCharacter));
    }

    @Test
    public void returnPlayCharacterStageWithInput() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);
        ConnectionState next = playCharacter.execute(connection, configuration);

        assertThat(next, sameInstance(playCharacter));
    }

    @Test
    public void interpretCommand() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        playCharacter.execute(connection, configuration);

        verify(commandInterpreter).interpret(text);
    }

    @Test
    public void doNotInterpretIfQueueIsEmpty() {
        playCharacter.execute(connection, configuration);

        verify(commandInterpreter, never()).interpret(anyString());
    }

    @Test
    public void executeCommand() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        String commandString = RandomStringUtils.randomAlphabetic(16);
        String arguments = RandomStringUtils.randomAlphabetic(15);

        when(result.getCommandString()).thenReturn(commandString);
        when(result.getCommandArguments()).thenReturn(arguments);

        playCharacter.execute(connection, configuration);

        verify(command).execute(connection, commandString, arguments, configuration);
    }

    @Test
    public void doNotExecuteCommandWithoutInput() {
        playCharacter.execute(connection, configuration);

        verify(command, never()).execute(eq(connection), anyString(), anyString(), eq(configuration));
    }

    @Test
    public void doNotExecuteForUnknownCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(TextName.UnknownCommand, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.UNKNOWN_COMMAND);

        playCharacter.execute(connection, configuration);

        verify(command, never()).execute(eq(connection), anyString(), anyString(), eq(configuration));
    }

    @Test
    public void returnThisStageForUnknownCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(TextName.UnknownCommand, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.UNKNOWN_COMMAND);

        ConnectionState next = playCharacter.execute(connection, configuration);

        assertThat(next, sameInstance(playCharacter));
    }

    @Test
    public void returnThisStageForAmbiguousCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(eq(TextName.AmbiguousCommand), eq(ConfigurationDefaults.SERVER_LOCALE), any())).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.AMBIGUOUS_COMMAND);

        ConnectionState next = playCharacter.execute(connection, configuration);

        assertThat(next, sameInstance(playCharacter));
    }

    @Test
    public void returnThisStageForUnknownResultStatus() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(result.getStatus()).thenReturn(CommandStatus.INSUFFICIENT_PERMISSIONS);

        ConnectionState next = playCharacter.execute(connection, configuration);

        assertThat(next, sameInstance(playCharacter));
    }

    @Test
    public void displayCommandUnknownMessageForUnknownCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(TextName.UnknownCommand, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.UNKNOWN_COMMAND);

        playCharacter.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(responseMessage));
    }

    @Test
    public void displayCommandAmbiguousMessageForAmbiguousCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(eq(TextName.AmbiguousCommand), eq(ConfigurationDefaults.SERVER_LOCALE), any())).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.AMBIGUOUS_COMMAND);

        playCharacter.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(responseMessage));
    }
}
