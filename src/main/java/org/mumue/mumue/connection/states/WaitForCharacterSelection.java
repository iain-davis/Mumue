package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForCharacterSelection implements ConnectionState {
    private final Injector injector;
    private final CharacterDao characterDao;
    private final TextMaker textMaker;

    @Inject
    public WaitForCharacterSelection(Injector injector, CharacterDao characterDao, TextMaker textMaker) {
        this.injector = injector;
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
            return injector.getInstance(CharacterSelectionPrompt.class);
        }
        connection.setCharacter(characterDao.getCharacter(characterId));
        return injector.getInstance(EnterUniverse.class);
    }
}
