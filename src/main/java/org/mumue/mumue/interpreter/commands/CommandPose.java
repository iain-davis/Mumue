package org.mumue.mumue.interpreter.commands;

import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;

public class CommandPose implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void execute(Connection connection, String command, String arguments, Configuration configuration) {
        String s = command.equals(";") ? "" : " ";
        GameCharacter character = connection.getCharacter();
        String output = character.getName() + s + arguments + "\\r\\n";
        for (Connection otherConnection : connectionManager.getConnections()) {
            if (otherConnection.getCharacter().getLocationId() == character.getLocationId()) {
                otherConnection.getOutputQueue().push(output);
            }
        }
    }
}
