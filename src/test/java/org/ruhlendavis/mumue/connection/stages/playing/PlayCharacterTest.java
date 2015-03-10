package org.ruhlendavis.mumue.connection.stages.playing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.interpreter.CommandInterpreter;
import org.ruhlendavis.mumue.interpreter.CommandResult;
import org.ruhlendavis.mumue.interpreter.CommandStatus;
import org.ruhlendavis.mumue.interpreter.commands.Command;
import org.ruhlendavis.mumue.player.PlayerBuilder;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class PlayCharacterTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Command command;
    @Mock CommandResult result;
    @Mock CommandInterpreter commandInterpreter;
    @Mock TextMaker textMaker;
    @Mock Configuration configuration;
    @InjectMocks PlayCharacter stage;

    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final GameCharacter character = new GameCharacter();
    private final Connection connection = new Connection(configuration).withPlayer(new PlayerBuilder().withLocale(locale).build()).withCharacter(character);

    @Before
    public void beforeEach() {
        when(result.getStatus()).thenReturn(CommandStatus.OK);
        when(commandInterpreter.interpret(anyString())).thenReturn(result);
        when(result.getCommand()).thenReturn(command);
    }

    @Test
    public void returnPlayCharacterStageWithoutInput() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void returnPlayCharacterStageWithInput() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void interpretCommand() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        stage.execute(connection, configuration);

        verify(commandInterpreter).interpret(text);
    }

    @Test
    public void doNotInterpretIfQueueIsEmpty() {
        stage.execute(connection, configuration);

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

        stage.execute(connection, configuration);

        verify(command).execute(connection, commandString, arguments, configuration);
    }

    @Test
    public void doNotExecuteCommandWithoutInput() {
        stage.execute(connection, configuration);

        verify(command, never()).execute(eq(connection), anyString(), anyString(), eq(configuration));
    }

    @Test
    public void doNotExecuteForUnknownCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(TextName.UnknownCommand, locale)).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.UNKNOWN_COMMAND);

        stage.execute(connection, configuration);

        verify(command, never()).execute(eq(connection), anyString(), anyString(), eq(configuration));
    }

    @Test
    public void returnThisStageForUnknownCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(TextName.UnknownCommand, locale)).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.UNKNOWN_COMMAND);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void returnThisStageForAmbiguousCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(eq(TextName.AmbiguousCommand), eq(locale), any())).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.AMBIGUOUS_COMMAND);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void returnThisStageForUnknownResultStatus() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(result.getStatus()).thenReturn(CommandStatus.INSUFFICIENT_PERMISSIONS);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void displayCommandUnknownMessageForUnknownCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(TextName.UnknownCommand, locale)).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.UNKNOWN_COMMAND);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(responseMessage));
    }

    @Test
    public void displayCommandAmbiguousMessageForAmbiguousCommand() {
        String responseMessage = RandomStringUtils.randomAlphabetic(25);
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(textMaker.getText(eq(TextName.AmbiguousCommand), eq(locale), any())).thenReturn(responseMessage);
        when(result.getStatus()).thenReturn(CommandStatus.AMBIGUOUS_COMMAND);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(responseMessage));
    }
}
