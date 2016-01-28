package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
class CharacterSelectionHandler implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final CharacterDao characterDao;
    private final TextMaker textMaker;

    @Inject
    public CharacterSelectionHandler(ConnectionStateProvider connectionStateProvider, CharacterDao characterDao, TextMaker textMaker) {
        this.connectionStateProvider = connectionStateProvider;
        this.characterDao = characterDao;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        String selection = connection.getInputQueue().pop();
        Long characterId = connection.getMenuOptionIds().get(selection);
        if (characterId == null) {
            String text = textMaker.getText(TextName.InvalidOption, connection.getLocale());
            connection.getOutputQueue().push(text);
            return connectionStateProvider.get(CharacterSelectionPrompt.class);
        }
        connection.setCharacter(characterDao.getCharacter(characterId));
        return connectionStateProvider.get(EnterUniverse.class);
    }
}
