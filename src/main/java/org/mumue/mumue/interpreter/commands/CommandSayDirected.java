package org.mumue.mumue.interpreter.commands;

import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CommandSayDirected implements Command {
    private ConnectionManager connectionManager = new ConnectionManager();
    private TextMaker textMaker = new TextMaker();

    @Override
    public void execute(Connection connection, String command, String arguments, ApplicationConfiguration configuration) {
        String sayText = arguments.substring(arguments.indexOf(" ") + 1);
        if (includesMessageText(arguments) || StringUtils.isBlank(sayText)) {
            String text = textMaker.getText(TextName.MissingSayText, connection.getLocale());
            connection.getOutputQueue().push(text);
            return;
        }

        String targetName = findTarget(extractTargetName(arguments));
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

    private String extractTargetName(String arguments) {
        return arguments.substring(0, arguments.indexOf(" "));
    }

    private boolean includesMessageText(String arguments) {
        return arguments.indexOf(" ") == -1;
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
