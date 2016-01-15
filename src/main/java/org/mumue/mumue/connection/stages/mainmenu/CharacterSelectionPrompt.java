package org.mumue.mumue.connection.stages.mainmenu;

import com.google.inject.Injector;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import javax.inject.Inject;

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
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        StringBuilder builder = new StringBuilder("\\r\\n");
        Integer optionCount = 0;
        for (GameCharacter character : characterDao.getCharacters(connection.getPlayer().getId())) {
            optionCount++;
            connection.getMenuOptionIds().put(optionCount.toString(), character.getId());
            builder.append(optionCount.toString()).append(") ");
            builder.append(character.getName()).append("\\r\\n");
        }
        builder.append("\\r\\n");
        connection.getOutputQueue().push(builder.toString());
        String text = textMaker.getText(TextName.CharacterSelectionPrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return injector.getInstance(WaitForCharacterSelection.class);
    }
}
