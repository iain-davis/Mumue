package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PasswordPrompt implements ConnectionState {
    private final StateCollection stateCollection;
    private final TextMaker textMaker;

    @Inject
    public PasswordPrompt(StateCollection stateCollection, TextMaker textMaker) {
        this.stateCollection = stateCollection;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.PasswordPrompt, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return stateCollection.get(StateName.PasswordPromptHandler);
    }
}
