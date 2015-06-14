package org.mumue.mumue.connection.stages.login;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextName;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;

public class Welcome implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String text = textMaker.getText(TextName.Welcome, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return new LoginPrompt();
    }
}
