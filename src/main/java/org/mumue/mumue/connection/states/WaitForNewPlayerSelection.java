package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.connection.states.LoginIdPrompt;
import org.mumue.mumue.connection.states.PasswordPrompt;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForNewPlayerSelection implements ConnectionState {
    private final Injector injector;
    private final TextMaker textMaker;

    @Inject
    public WaitForNewPlayerSelection(Injector injector, TextMaker textMaker) {
        this.injector = injector;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
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
                return injector.getInstance(LoginIdPrompt.class);
            }
        }
    }
}
