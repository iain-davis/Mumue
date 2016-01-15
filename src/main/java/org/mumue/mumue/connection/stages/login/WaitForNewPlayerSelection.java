package org.mumue.mumue.connection.stages.login;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForNewPlayerSelection implements ConnectionStage {
    private final Injector injector;
    private final TextMaker textMaker;

    @Inject
    public WaitForNewPlayerSelection(Injector injector, TextMaker textMaker) {
        this.injector = injector;
        this.textMaker = textMaker;
    }

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
                return injector.getInstance(PasswordPrompt.class);
            } else {
                return injector.getInstance(LoginPrompt.class);
            }
        }
    }
}
