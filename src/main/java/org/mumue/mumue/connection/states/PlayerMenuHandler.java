package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Singleton;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
public class PlayerMenuHandler implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final CharacterDao characterDao;
    private final TextMaker textMaker;

    @Inject
    public PlayerMenuHandler(ConnectionStateProvider connectionStateProvider, CharacterDao characterDao, TextMaker textMaker) {
        this.connectionStateProvider = connectionStateProvider;
        this.characterDao = characterDao;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        String input = connection.getInputQueue().pop();
        switch (input.toUpperCase()) {
            case "P":
                if (playerHasCharacters(connection.getPlayer().getId())) {
                    return connectionStateProvider.get(CharacterSelectionPrompt.class);
                }
                return handleCharacterNeeded(connection);
            case "C":
                return connectionStateProvider.get(UniverseSelectionPrompt.class);
            default:
                return handleInvalidOption(connection);
        }
    }

    private boolean playerHasCharacters(long playerId) {
        return !characterDao.getCharacters(playerId).isEmpty();
    }

    private ConnectionState handleCharacterNeeded(Connection connection) {
        String text = textMaker.getText(TextName.CharacterNeeded, connection.getLocale());
        connection.getOutputQueue().push(text);
        return connectionStateProvider.get(UniverseSelectionPrompt.class);
    }

    private ConnectionState handleInvalidOption(Connection connection) {
        String text = textMaker.getText(TextName.InvalidOption, connection.getLocale());
        connection.getOutputQueue().push(text);
        return connectionStateProvider.get(PlayerMenuPrompt.class);
    }
}
