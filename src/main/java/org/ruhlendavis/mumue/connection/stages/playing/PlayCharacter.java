package org.ruhlendavis.mumue.connection.stages.playing;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.interpreter.CommandInterpreter;
import org.ruhlendavis.mumue.interpreter.CommandResult;
import org.ruhlendavis.mumue.interpreter.CommandStatus;
import org.ruhlendavis.mumue.interpreter.commands.Command;

public class PlayCharacter implements ConnectionStage {
    private CommandInterpreter commandInterpreter = new CommandInterpreter();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        CommandResult result = commandInterpreter.interpret(connection.getInputQueue().pop());

        if (result.getStatus() == CommandStatus.OK) {
            Command command = result.getCommands().get(0);
            command.execute(connection, result.getCommandString(), result.getCommandArguments(), configuration);
        }

        return this;
    }
}
