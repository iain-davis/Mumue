package org.ruhlendavis.meta;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.ruhlendavis.meta.commands.CommandPose;
import org.ruhlendavis.meta.commands.CommandSay;

public class CommandInterpreterTest {
    CommandInterpreter interpreter = new CommandInterpreter();

    @Before
    public void beforeEach() {
        CommandInterpreter.clearCommands();
    }

    @Test
    public void interpretFindsATokenCommand() {
        String token = "~";
        CommandInterpreter.putTokenCommand(token, new CommandSay());
        CommandResult result = interpreter.interpret(token);
        assertEquals(CommandStatus.OK, result.getStatus());
        assertEquals(CommandSay.class, result.getCommands().get(0).getClass());
    }

    @Test
    public void interpretSetsTokenCommand() {
        String token = "~";
        CommandInterpreter.putTokenCommand(token, new CommandSay());
        CommandResult result = interpreter.interpret(token);
        assertEquals(token, result.getCommandString());
    }

    @Test
    public void interpretWithTokenSetsArguments() {
        String token = "~";
        CommandInterpreter.putTokenCommand(token, new CommandSay());
        String arguments = RandomStringUtils.randomAlphabetic(13);
        String line = token + arguments;
        CommandResult result = interpreter.interpret(line);
        assertEquals(arguments, result.getCommandArguments());
    }

    @Test
    public void interpretFindsTwoTokens() {
        CommandInterpreter.putTokenCommand("~", new CommandSay());
        CommandInterpreter.putTokenCommand("&", new CommandSay());
        CommandResult result1 = interpreter.interpret("~");
        CommandResult result2 = interpreter.interpret("&");
        assertEquals(CommandStatus.OK, result1.getStatus());
        assertEquals(CommandStatus.OK, result2.getStatus());
        assertEquals(CommandSay.class, result1.getCommands().get(0).getClass());
        assertEquals(CommandSay.class, result2.getCommands().get(0).getClass());
    }

    @Test
    public void interpretRejectsUnknownToken() {
        CommandInterpreter.putTokenCommand("~", new CommandSay());
        CommandResult result = interpreter.interpret("(");
        assertEquals(CommandStatus.UNKNOWN_COMMAND, result.getStatus());
        assertEquals(0, result.getCommands().size());
    }

    @Test
    public void interpretFindsACommand() {
        String command = RandomStringUtils.randomAlphabetic(13);
        CommandInterpreter.putCommand(command, new CommandSay());
        CommandResult result = interpreter.interpret(command);
        assertEquals(CommandStatus.OK, result.getStatus());
        assertEquals(CommandSay.class, result.getCommands().get(0).getClass());
    }

    @Test
    public void interpretSetsCommandString() {
        String command = RandomStringUtils.randomAlphabetic(13);
        String line = command + " " + RandomStringUtils.randomAlphabetic(13);
        CommandInterpreter.putCommand(command, new CommandSay());
        CommandResult result = interpreter.interpret(line);
        assertEquals(command, result.getCommandString());
    }

    @Test
    public void interpretSetsCommandArguments() {
        String command = RandomStringUtils.randomAlphabetic(12);
        String arguments = RandomStringUtils.randomAlphabetic(13);
        String line = command + " " + arguments;
        CommandInterpreter.putCommand(command, new CommandSay());
        CommandResult result = interpreter.interpret(line);
        assertEquals(arguments, result.getCommandArguments());
    }

    @Test
    public void interpretFindsACommandEvenThoughCaseDiffers() {
        String command = RandomStringUtils.randomAlphabetic(13).toUpperCase();
        CommandInterpreter.putCommand(command, new CommandSay());
        CommandResult result = interpreter.interpret(command);
        assertEquals(CommandStatus.OK, result.getStatus());
        assertEquals(CommandSay.class, result.getCommands().get(0).getClass());
    }

    @Test
    public void interpretWithPartialMatchReturnsCommand() {
        String command = RandomStringUtils.randomAlphabetic(13);
        CommandInterpreter.putCommand(command, new CommandPose());
        CommandResult result = interpreter.interpret(command + RandomStringUtils.randomAlphabetic(3));
        assertEquals(CommandStatus.OK, result.getStatus());
    }

    @Test
    public void interpretWithMultipleMatchesReturnsAmbiguous() {
        String command = RandomStringUtils.randomAlphabetic(13);
        CommandInterpreter.putCommand(command.substring(0, 3), new CommandSay());
        CommandInterpreter.putCommand(command.substring(0, 4), new CommandPose());
        CommandResult result = interpreter.interpret(command);
        assertEquals(CommandStatus.AMBIGUOUS_COMMAND, result.getStatus());
    }

    @Test
    public void interpretWithMultipleMatchesReturnsBothCommands() {
        String command = RandomStringUtils.randomAlphabetic(13);
        CommandInterpreter.putCommand(command.substring(0, 3), new CommandSay());
        CommandInterpreter.putCommand(command.substring(0, 4), new CommandPose());
        CommandResult result = interpreter.interpret(command);
        assertEquals(CommandStatus.AMBIGUOUS_COMMAND, result.getStatus());
        assertEquals(2, result.getCommands().size());
    }
}
