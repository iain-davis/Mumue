package org.mumue.mumue.interpreter.commands;

import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.importer.GlobalConstants;

public class CommandPose implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public void execute(Connection connection, String command, String arguments, ApplicationConfiguration configuration) {
        String space = Character.isAlphabetic(arguments.charAt(0)) ? " " : "";
        GameCharacter character = connection.getCharacter();
        connectionManager.poseTo(character.getLocationId(), character.getName(), space + arguments);
    }
}
