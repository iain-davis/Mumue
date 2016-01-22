package org.mumue.mumue.connection.states.login;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PasswordPrompt implements ConnectionState {
    private final Injector injector;
    private final TextMaker textMaker;

    @Inject
    public PasswordPrompt(Injector injector, TextMaker textMaker) {
        this.injector = injector;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.PasswordPrompt, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return injector.getInstance(WaitForPassword.class);
    }
}
