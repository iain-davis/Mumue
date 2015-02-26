package org.ruhlendavis.mumue.connection.stages;

import org.ruhlendavis.mumue.components.CharacterDao;
import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
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
        String locale = connection.getPlayer().getLocale();
        String serverLocale = configuration.getServerLocale();
        String text = textMaker.getText(TextName.CharacterSelectionPrompt, locale, serverLocale);
        connection.getOutputQueue().push(text);
        return new WaitForCharacterSelection();
    }
}
