package org.mumue.mumue.connection.stages.login;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PasswordPrompt implements ConnectionStage {
    private final Injector injector;
    private final TextMaker textMaker;

    @Inject
    public PasswordPrompt(Injector injector, TextMaker textMaker) {
        this.injector = injector;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String text = textMaker.getText(TextName.PasswordPrompt, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return injector.getInstance(WaitForPassword.class);
    }
}
