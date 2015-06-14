package org.mumue.mumue.connection.stages.mainmenu;

import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForPlayerMenuChoice implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    private CharacterDao dao = new CharacterDao();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        String input = connection.getInputQueue().pop();
        switch (input.toUpperCase()) {
            case "P":
                if (playerHasCharacters(connection.getPlayer().getId())) {
                    return new CharacterSelectionPrompt();
                }
                return handleCharacterNeeded(connection);
            case "C":
                return new UniverseSelectionPrompt();
            default:
                return handleInvalidOption(connection);
        }
    }

    private boolean playerHasCharacters(long playerId) {
        return !dao.getCharacters(playerId).isEmpty();
    }

    private ConnectionStage handleCharacterNeeded(Connection connection) {
        String locale = connection.getPlayer().getLocale();
        String text = textMaker.getText(TextName.CharacterNeeded, locale);
        connection.getOutputQueue().push(text);
        return new UniverseSelectionPrompt();
    }

    private ConnectionStage handleInvalidOption(Connection connection) {
        String locale = connection.getPlayer().getLocale();
        String text = textMaker.getText(TextName.InvalidOption, locale);
        connection.getOutputQueue().push(text);
        return new DisplayPlayerMenu();
    }
}
