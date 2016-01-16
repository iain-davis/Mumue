package org.mumue.mumue.interpreter.commands;

import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;

public class CommandPose implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void execute(Connection connection, String command, String arguments, ApplicationConfiguration configuration) {
        String s = command.equals(";") ? "" : " ";
        GameCharacter character = connection.getCharacter();
        String text = s + arguments + "\\r\\n";
        connectionManager.poseTo(character.getLocationId(), character.getName(), text);
    }
}
