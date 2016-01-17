package org.mumue.mumue.interpreter.commands;

import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.importer.GlobalConstants;

public class CommandSay implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void execute(Connection connection, String command, String arguments, ApplicationConfiguration configuration) {
        String message = ", \"" + arguments + "\"";
        GameCharacter character = connection.getCharacter();
        connectionManager.poseTo(character.getLocationId(), character.getName(), " says" + message, connection);
        connection.getOutputQueue().push("You say" + message + GlobalConstants.TCP_LINE_SEPARATOR);
    }
}
