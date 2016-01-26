package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
public class WelcomeDisplay implements ConnectionState {
    private final ConnectionStateService connectionStateService;
    private final TextMaker textMaker;

    @Inject
    public WelcomeDisplay(ConnectionStateService connectionStateService, TextMaker textMaker) {
        this.connectionStateService = connectionStateService;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.Welcome, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        if (connection.getPortConfiguration().isSupportsMenus()) {
            return connectionStateService.get(LoginIdPrompt.class);
        } else {
            return connectionStateService.get(CommandDrivenPrompt.class);
        }
    }
}
