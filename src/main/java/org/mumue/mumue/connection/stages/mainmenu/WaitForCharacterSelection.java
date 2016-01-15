package org.mumue.mumue.connection.stages.mainmenu;

import com.google.inject.Injector;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.connection.stages.playing.EnterUniverse;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import javax.inject.Inject;

public class WaitForCharacterSelection implements ConnectionStage {
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
    public ConnectionStage execute(Connection connection, Configuration configuration) {
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
