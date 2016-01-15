package org.mumue.mumue.connection.stages.mainmenu;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForPlayerMenuChoice implements ConnectionStage {
    private final Injector injector;
    private final CharacterDao characterDao;
    private final TextMaker textMaker;

    @Inject
    public WaitForPlayerMenuChoice(Injector injector, CharacterDao characterDao, TextMaker textMaker) {
        this.injector = injector;
        this.characterDao = characterDao;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        String input = connection.getInputQueue().pop();
        switch (input.toUpperCase()) {
            case "P":
                if (playerHasCharacters(connection.getPlayer().getId())) {
                    return injector.getInstance(CharacterSelectionPrompt.class);
                }
                return handleCharacterNeeded(connection);
            case "C":
                return injector.getInstance(UniverseSelectionPrompt.class);
            default:
                return handleInvalidOption(connection);
        }
    }

    private boolean playerHasCharacters(long playerId) {
        return !characterDao.getCharacters(playerId).isEmpty();
    }

    private ConnectionStage handleCharacterNeeded(Connection connection) {
        String locale = connection.getPlayer().getLocale();
        String text = textMaker.getText(TextName.CharacterNeeded, locale);
        connection.getOutputQueue().push(text);
        return injector.getInstance(UniverseSelectionPrompt.class);
    }

    private ConnectionStage handleInvalidOption(Connection connection) {
        String locale = connection.getPlayer().getLocale();
        String text = textMaker.getText(TextName.InvalidOption, locale);
        connection.getOutputQueue().push(text);
        return injector.getInstance(DisplayPlayerMenu.class);
    }
}
