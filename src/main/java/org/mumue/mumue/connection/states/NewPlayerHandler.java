package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Singleton;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
public class NewPlayerHandler implements ConnectionState {
    private final ConnectionStateService connectionStateService;
    private final TextMaker textMaker;

    @Inject
    public NewPlayerHandler(ConnectionStateService connectionStateService, TextMaker textMaker) {
        this.connectionStateService = connectionStateService;
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
                return connectionStateService.get(PasswordPrompt.class);
            } else {
                return connectionStateService.get(LoginIdPrompt.class);
            }
        }
    }
}
