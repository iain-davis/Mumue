package org.mumue.mumue.connection.stages.mainmenu;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CharacterSelectionPrompt implements ConnectionStage {
    private final Injector injector;
    private final TextMaker textMaker;
    private final CharacterDao characterDao;

    @Inject
    public CharacterSelectionPrompt(Injector injector, TextMaker textMaker, CharacterDao characterDao) {
        this.injector = injector;
        this.textMaker = textMaker;
        this.characterDao = characterDao;
    }

    @Override
    public ConnectionStage execute(Connection connection, ApplicationConfiguration configuration) {
        StringBuilder builder = new StringBuilder(GlobalConstants.NEW_LINE);
        Integer optionCount = 0;
        for (GameCharacter character : characterDao.getCharacters(connection.getPlayer().getId())) {
            optionCount++;
            connection.getMenuOptionIds().put(optionCount.toString(), character.getId());
            builder.append(optionCount.toString()).append(") ");
            builder.append(character.getName()).append(GlobalConstants.NEW_LINE);
        }
        builder.append(GlobalConstants.NEW_LINE);
        connection.getOutputQueue().push(builder.toString());
        String text = textMaker.getText(TextName.CharacterSelectionPrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return injector.getInstance(WaitForCharacterSelection.class);
    }
}
