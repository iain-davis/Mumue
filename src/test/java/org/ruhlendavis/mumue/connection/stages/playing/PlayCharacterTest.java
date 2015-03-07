package org.ruhlendavis.mumue.connection.stages.playing;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.interpreter.CommandInterpreter;
import org.ruhlendavis.mumue.interpreter.CommandResult;
import org.ruhlendavis.mumue.interpreter.CommandStatus;
import org.ruhlendavis.mumue.interpreter.commands.Command;

@RunWith(MockitoJUnitRunner.class)
public class PlayCharacterTest {
    private final GameCharacter character = new GameCharacter();
    private final Connection connection = new Connection().withCharacter(character);

    @Mock Command command;
    @Mock CommandResult result;
    @Mock CommandInterpreter commandInterpreter;
    @Mock Configuration configuration;
    @InjectMocks PlayCharacter stage;

    @Before
    public void beforeEach() {
        when(result.getStatus()).thenReturn(CommandStatus.OK);
        when(commandInterpreter.interpret(anyString())).thenReturn(result);
        when(result.getCommands()).thenReturn(Arrays.asList(command));
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
    public void doNotExecuteCommandWithoutOK() {
        String text = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(text);

        when(result.getStatus()).thenReturn(CommandStatus.AMBIGUOUS_COMMAND);
        stage.execute(connection, configuration);

        verify(command, never()).execute(eq(connection), anyString(), anyString(), eq(configuration));

    }
}
