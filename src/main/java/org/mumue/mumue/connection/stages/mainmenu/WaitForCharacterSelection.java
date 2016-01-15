package org.mumue.mumue.connection.stages.mainmenu;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.playing.EnterUniverse;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.connection.stages.ConnectionStage;

public class WaitForCharacterSelection implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    private CharacterDao dao = new CharacterDao();

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
            return new CharacterSelectionPrompt();
        }
        connection.setCharacter(dao.getCharacter(characterId));
        return new EnterUniverse();
    }
}
