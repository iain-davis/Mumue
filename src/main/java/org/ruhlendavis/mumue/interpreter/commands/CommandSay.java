package org.ruhlendavis.mumue.interpreter.commands;

import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.ConnectionManager;

public class CommandSay implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void execute(GameCharacter character, String command, String arguments) {
        String message = ", \"" + arguments + "\"\\r\\n";
        String youSay = "You say" + message;
        String otherSay = character.getName() + " says" + message;
        for (Connection connection : connectionManager.getConnections()) {
            GameCharacter connectionCharacter = connection.getCharacter();
            if (connectionCharacter.equals(character)) {
                connection.getOutputQueue().push(youSay);
            } else if (connectionCharacter.getLocationId() == character.getLocationId()) {
                connection.getOutputQueue().push(otherSay);
            }
        }
    }
}
