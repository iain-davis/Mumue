package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.components.CharacterDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

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
                return handleCharacterNeeded(connection, configuration);
            case "C":
                return new UniverseSelectionPrompt();
            default:
                return handleInvalidOption(connection, configuration);
        }
    }

    private boolean playerHasCharacters(long playerId) {
        return !dao.getCharacters(playerId).isEmpty();
    }

    private ConnectionStage handleCharacterNeeded(Connection connection, Configuration configuration) {
        String locale = connection.getPlayer().getLocale();
        String serverLocale = configuration.getServerLocale();
        String text = textMaker.getText(TextName.CharacterNeeded, locale, serverLocale);
        connection.getOutputQueue().push(text);
        return new UniverseSelectionPrompt();
    }

    private ConnectionStage handleInvalidOption(Connection connection, Configuration configuration) {
        String locale = connection.getPlayer().getLocale();
        String serverLocale = configuration.getServerLocale();
        String text = textMaker.getText(TextName.InvalidOption, locale, serverLocale);
        connection.getOutputQueue().push(text);
        return new DisplayPlayerMenu();
    }
}
