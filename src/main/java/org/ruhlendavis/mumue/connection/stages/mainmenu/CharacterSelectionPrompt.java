package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.components.character.CharacterDao;
import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

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
