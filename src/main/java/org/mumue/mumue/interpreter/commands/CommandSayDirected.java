package org.mumue.mumue.interpreter.commands;

import java.util.Map;
import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CommandSayDirected implements Command {
    private final ConnectionManager connectionManager;
    private final TextMaker textMaker;

    @Inject
    public CommandSayDirected(ConnectionManager connectionManager, TextMaker textMaker) {
        this.connectionManager = connectionManager;
        this.textMaker = textMaker;
    }

    @Override
    public void execute(Connection connection, String command, String arguments, ApplicationConfiguration configuration) {
        String sayText = arguments.substring(arguments.indexOf(" ") + 1);
        if (StringUtils.isBlank(sayText)) {
            String text = textMaker.getText(TextName.MissingSayText, connection.getLocale());
            connection.getOutputQueue().push(text);
            return;
        }

        String targetNameSpecified = extractTargetName(arguments);
        String targetName = findTarget(targetNameSpecified);
        if (targetName.equals("")) {
            Map<String, String> variables = ImmutableMap.of("target name", targetNameSpecified);
            String text = textMaker.getText(TextName.TargetBeingNotFound, connection.getLocale(), variables);
            connection.getOutputQueue().push(text);
            return;
        }

        GameCharacter character = connection.getCharacter();
        String message = " says to " + targetName + ", \"" + sayText + "\"";
        connectionManager.poseTo(character.getLocationId(), character.getName(), message);
    }

    private String extractTargetName(String arguments) {
        return arguments.substring(0, arguments.indexOf(" "));
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
