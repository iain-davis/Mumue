package org.ruhlendavis.mumue.interpreter.commands;

import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.ConnectionManager;

public class CommandPose implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void execute(GameCharacter character, String command, String arguments) {
        String s = command.equals(";") ? "" : " ";
        String output = character.getName() + s + arguments + "\\r\\n";
        for (Connection connection : connectionManager.getConnections()) {
            if (connection.getCharacter().getLocationId() == character.getLocationId()) {
                connection.getOutputQueue().push(output);
            }
        }
    }
}
