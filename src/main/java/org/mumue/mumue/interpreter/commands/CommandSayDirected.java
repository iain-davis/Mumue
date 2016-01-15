package org.mumue.mumue.interpreter.commands;

import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CommandSayDirected implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();
    private TextMaker textMaker = new TextMaker();

    @Override
    public void execute(Connection connection, String command, String arguments, Configuration configuration) {
        int spacePosition = arguments.indexOf(" ");
        if (spacePosition == -1) {
            respondToLackOfMessage(connection);
            return;
        }
        String sayText = arguments.substring(spacePosition + 1);

        if (StringUtils.isBlank(sayText)) {
            respondToLackOfMessage(connection);
            return;
        }

        String targetName = findTarget(arguments.substring(0, spacePosition));
        if (targetName.equals("")) {
            String text = textMaker.getText(TextName.TargetBeingNotFound, connection.getLocale());
            connection.getOutputQueue().push(text);
            return;
        }

        GameCharacter character = connection.getCharacter();
        String message = character.getName() + " says to " + targetName + ", \"" + sayText + "\"\\r\\n";
        for (Connection otherConnection : connectionManager.getConnections()) {
            if (character.getLocationId() == otherConnection.getCharacter().getLocationId()) {
                otherConnection.getOutputQueue().push(message);
            }
        }
    }

    private void respondToLackOfMessage(Connection connection) {
        String text = textMaker.getText(TextName.MissingSayText, connection.getLocale());
        connection.getOutputQueue().push(text);
    }

    private String findTarget(String matchString) {
        String matchLower = matchString.toLowerCase();
        for (Connection connection : connectionManager.getConnections()) {
            if (connection.getCharacter().getName().toLowerCase().startsWith(matchLower)) {
                return connection.getCharacter().getName();
            }
        }
        return "";
    }
}
