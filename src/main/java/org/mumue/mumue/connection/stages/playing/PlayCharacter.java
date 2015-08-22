package org.mumue.mumue.connection.stages.playing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mumue.mumue.interpreter.commands.Command;
import org.mumue.mumue.utility.StringUtilities;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.interpreter.CommandInterpreter;
import org.mumue.mumue.interpreter.CommandResult;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PlayCharacter implements ConnectionStage {
    private CommandInterpreter commandInterpreter = new CommandInterpreter();
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        CommandResult result = commandInterpreter.interpret(connection.getInputQueue().pop());

        switch (result.getStatus()) {
            case OK:
                return executeCommand(connection, configuration, result);
            case UNKNOWN_COMMAND:
                return respondTo(connection, getUnknownResponse(connection));
            case AMBIGUOUS_COMMAND:
                return respondTo(connection, getAmbiguousResponse(connection, result.getCommands()));
            default:
                return this;
        }
    }

    private ConnectionStage respondTo(Connection connection, String response) {
        connection.getOutputQueue().push(response);
        return this;
    }

    private ConnectionStage executeCommand(Connection connection, Configuration configuration, CommandResult result) {
        Command command = result.getCommand();
        command.execute(connection, result.getCommandString(), result.getCommandArguments(), configuration);
        return this;
    }

    private String getAmbiguousResponse(Connection connection, List<String> commands) {
        Map<String, String> variables = new HashMap<>();
        variables.put("commands", StringUtilities.commaIfy(commands, "and"));
        return textMaker.getText(TextName.AmbiguousCommand, connection.getLocale(), variables);
    }

    private String getUnknownResponse(Connection connection) {
        return textMaker.getText(TextName.UnknownCommand, connection.getLocale());
    }
}