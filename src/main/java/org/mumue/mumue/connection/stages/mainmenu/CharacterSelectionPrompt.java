package org.mumue.mumue.connection.stages.mainmenu;

import org.mumue.mumue.text.TextName;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;

public class CharacterSelectionPrompt implements ConnectionStage {
    TextMaker textMaker = new TextMaker();
    CharacterDao dao = new CharacterDao();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        StringBuilder builder = new StringBuilder("\\r\\n");
        Integer optionCount = 0;
        for (GameCharacter character : dao.getCharacters(connection.getPlayer().getId())) {
            optionCount++;
            connection.getMenuOptionIds().put(optionCount.toString(), character.getId());
            builder.append(optionCount.toString()).append(") ");
            builder.append(character.getName()).append("\\r\\n");
        }
        builder.append("\\r\\n");
        connection.getOutputQueue().push(builder.toString());
        String text = textMaker.getText(TextName.CharacterSelectionPrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return new WaitForCharacterSelection();
    }
}
