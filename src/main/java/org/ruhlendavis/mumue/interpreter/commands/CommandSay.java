package org.ruhlendavis.mumue.interpreter.commands;

import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.ConnectionManager;

public class CommandSay implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void execute(Connection connection, String command, String arguments, Configuration configuration) {
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
