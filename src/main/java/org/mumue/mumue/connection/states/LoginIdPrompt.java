package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
class LoginIdPrompt implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final TextMaker textMaker;

    @Inject
    public LoginIdPrompt(ConnectionStateProvider connectionStateProvider, TextMaker textMaker) {
        this.connectionStateProvider = connectionStateProvider;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.LoginPrompt, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return connectionStateProvider.get(LoginIdHandler.class);
    }
}
