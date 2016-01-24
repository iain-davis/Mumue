package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CharacterSelectionPrompt implements ConnectionState {
    private final WaitForCharacterSelection waitForCharacterSelection;
    private final TextMaker textMaker;
    private final CharacterDao characterDao;

    @Inject
    @Singleton
    public CharacterSelectionPrompt(WaitForCharacterSelection waitForCharacterSelection, TextMaker textMaker, CharacterDao characterDao) {
        this.waitForCharacterSelection = waitForCharacterSelection;
        this.textMaker = textMaker;
        this.characterDao = characterDao;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        StringBuilder builder = new StringBuilder(GlobalConstants.TCP_LINE_SEPARATOR);
        Integer optionCount = 0;
        for (GameCharacter character : characterDao.getCharacters(connection.getPlayer().getId())) {
            optionCount++;
            connection.getMenuOptionIds().put(optionCount.toString(), character.getId());
            builder.append(optionCount.toString()).append(") ");
            builder.append(character.getName()).append(GlobalConstants.TCP_LINE_SEPARATOR);
        }
        builder.append(GlobalConstants.TCP_LINE_SEPARATOR);
        connection.getOutputQueue().push(builder.toString());
        String text = textMaker.getText(TextName.CharacterSelectionPrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return waitForCharacterSelection;
    }
}
