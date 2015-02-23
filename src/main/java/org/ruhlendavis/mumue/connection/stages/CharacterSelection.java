package org.ruhlendavis.mumue.connection.stages;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class CharacterSelection implements ConnectionStage {
    TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String locale = connection.getPlayer().getLocale();
        String serverLocale = configuration.getServerLocale();
        String text = textMaker.getText(TextName.CharacterSelectionPrompt, locale, serverLocale);
        connection.getOutputQueue().push(text);
        return new WaitForCharacterSelection();
    }
}
