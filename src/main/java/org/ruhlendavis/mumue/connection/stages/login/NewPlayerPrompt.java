package org.ruhlendavis.mumue.connection.stages.login;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class NewPlayerPrompt implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String text = textMaker.getText(TextName.NewPlayerPrompt, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return new WaitForNewPlayerSelection();
    }
}
