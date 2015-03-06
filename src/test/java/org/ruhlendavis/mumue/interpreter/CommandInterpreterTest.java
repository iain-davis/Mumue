package org.ruhlendavis.mumue.interpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.ruhlendavis.mumue.interpreter.commands.Command;
import org.ruhlendavis.mumue.interpreter.commands.CommandPose;
import org.ruhlendavis.mumue.interpreter.commands.CommandSay;

public class CommandInterpreterTest {
    private final String minimumPartial = RandomStringUtils.randomAlphabetic(17).toLowerCase();
    private final Map<String, CommandSyntax> commandList = new HashMap<>();
    private final CommandSay command = new CommandSay();

    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock CommandListBuilder commandListBuilder;
    @InjectMocks CommandInterpreter interpreter = new CommandInterpreter();

    @Before
    public void beforeEach() {
        CommandSyntax syntax = new CommandSyntax();
        syntax.setCommand(command);
        commandList.put(minimumPartial, syntax);
        when(commandListBuilder.build()).thenReturn(commandList);
    }

    @Test
    public void interpretNeverReturnsNull() {
        assertNotNull(interpreter.interpret(RandomStringUtils.randomAlphabetic(16)));
    }

    @Test
    public void interpretReturnsCommandOKWhenFound() {
        CommandResult result = interpreter.interpret(minimumPartial);
        assertThat(result.getStatus(), equalTo(CommandStatus.OK));
    }

    @Test
    public void interpretReturnsCommandWhenFound() {
        CommandResult result = interpreter.interpret(minimumPartial);

        List<Command> commands = result.getCommands();
        assertThat(commands.size(), equalTo(1));
        assertThat(commands.get(0), sameInstance(command));
    }

    @Test
    public void interpretWithoutArgumentsReturnsCommandString() {
        CommandResult result = interpreter.interpret(minimumPartial);
        assertThat(result.getCommandString(), equalTo(minimumPartial));
    }

    @Test
    public void interpretReturnsEntireCommandString() {
        String commandString = minimumPartial + RandomStringUtils.randomAlphabetic(4);
        CommandResult result = interpreter.interpret(commandString);
        assertThat(result.getCommandString(), equalTo(commandString));
    }

    @Test
    public void interpretWithArgumentsSetsCommandString() {
        String text = minimumPartial + " " + RandomStringUtils.randomAlphabetic(13);
        CommandResult result = interpreter.interpret(text);
        assertThat(result.getCommandString(), equalTo(minimumPartial));
    }

    @Test
    public void interpretReturnsArguments() {
        String arguments = RandomStringUtils.randomAlphabetic(13);
        String text = minimumPartial + " " + arguments;
        CommandResult result = interpreter.interpret(text);
        assertThat(result.getCommandArguments(), equalTo(arguments));
    }

    @Test
    public void interpretWithTokenReturnsCommandString() {
        String token = "~";
        commandList.put(token, new CommandSyntax());
        CommandResult result = interpreter.interpret(token);
        assertThat(result.getCommandString(), equalTo(token));
    }

    @Test
    public void interpretWithTokenAndArgumentsReturnsCommandString() {
        String token = "~";
        CommandSyntax syntax = new CommandSyntax();
        syntax.setToken(true);
        commandList.put(token, syntax);
        String arguments = RandomStringUtils.randomAlphabetic(13);
        String text = token + arguments;
        CommandResult result = interpreter.interpret(text);
        assertThat(result.getCommandString(), equalTo(token));
    }

    @Test
    public void interpretWithTokenAndArgumentsReturnsArguments() {
        String token = "~";
        CommandSyntax syntax = new CommandSyntax();
        syntax.setToken(true);
        commandList.put(token, syntax);
        String arguments = RandomStringUtils.randomAlphabetic(13);
        String text = token + arguments;
        CommandResult result = interpreter.interpret(text);
        assertThat(result.getCommandArguments(), equalTo(arguments));
    }

    @Test
    public void interpretCommandIsCaseInsensitive() {
        CommandResult result = interpreter.interpret(minimumPartial.toUpperCase());
        assertThat(result.getStatus(), equalTo(CommandStatus.OK));
    }

    @Test
    public void interpretMatchesAgainstMinimumPartial() {
        String text = minimumPartial + RandomStringUtils.randomAlphabetic(17);
        CommandResult result = interpreter.interpret(text);
        assertThat(result.getStatus(), equalTo(CommandStatus.OK));
    }

    @Test
    public void interpretWithUnknownCommandReturnsUnknown() {
        CommandResult result = interpreter.interpret(RandomStringUtils.randomAlphabetic(16));
        assertThat(result.getStatus(), equalTo(CommandStatus.UNKNOWN_COMMAND));
    }

    @Test
    public void interpretWithAmbiguityReturnsAmbiguous() {
        String text = RandomStringUtils.randomAlphabetic(13);
        commandList.put(text.substring(0, 3), new CommandSyntax());
        commandList.put(text.substring(0, 4), new CommandSyntax());
        CommandResult result = interpreter.interpret(text);
        assertThat(result.getStatus(), equalTo(CommandStatus.AMBIGUOUS_COMMAND));
    }

    @Test
    public void interpretWithAmbiguityReturnsBothCommands() {
        String text = RandomStringUtils.randomAlphabetic(13);
        CommandSay command1 = new CommandSay();
        CommandSyntax syntax1 = new CommandSyntax();
        syntax1.setCommand(command1);

        CommandPose command2 = new CommandPose();
        CommandSyntax syntax2 = new CommandSyntax();
        syntax2.setCommand(command2);

        commandList.put(text.substring(0, 3), syntax1);
        commandList.put(text.substring(0, 4), syntax2);

        CommandResult result = interpreter.interpret(text);
        List<Command> commands = result.getCommands();

        assertThat(commands.size(), equalTo(2));
        assertThat(commands, hasItem(command1));
        assertThat(commands, hasItem(command2));
    }
}
