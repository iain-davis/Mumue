package org.ruhlendavis.mumue.connection.stages.login;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class WaitForNewPlayerSelection implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().size() < 2) {
            return this;
        } else {
            String yes = textMaker.getText(TextName.Yes, configuration.getServerLocale());
            String loginId = connection.getInputQueue().pop();
            String answer = connection.getInputQueue().pop();
            if (answer.equals(yes)) {
                connection.getInputQueue().push(loginId);
                return new PasswordPrompt();
            } else {
                return new LoginPrompt();
            }
        }
    }
}
