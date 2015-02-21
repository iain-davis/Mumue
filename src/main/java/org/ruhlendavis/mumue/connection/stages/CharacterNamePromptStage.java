package org.ruhlendavis.mumue.connection.stages;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class CharacterNamePromptStage implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String text = textMaker.getText(TextName.CharacterNamePrompt, connection.getPlayer().getLocale(), configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return new WaitForCharacterNameStage();
    }
}
