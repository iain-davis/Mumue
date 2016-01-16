package org.mumue.mumue.interpreter.commands;

import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;

public class CommandSay implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void execute(Connection connection, String command, String arguments, ApplicationConfiguration configuration) {
        GameCharacter character = connection.getCharacter();
        String message = ", \"" + arguments + "\"\\r\\n";
        String youSay = "You say" + message;

        String otherSay = character.getName() + " says" + message;
        for (Connection otherConnection : connectionManager.getConnections()) {
            if (connection.equals(otherConnection)) {
                otherConnection.getOutputQueue().push(youSay);
            } else if (otherConnection.getCharacter().getLocationId() == character.getLocationId()) {
                otherConnection.getOutputQueue().push(otherSay);
            }
        }
    }
}
