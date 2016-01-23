package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WelcomeCommandsPrompt implements ConnectionState {
    private final StateCollection stateCollection;
    private final TextMaker textMaker;

    @Inject
    public WelcomeCommandsPrompt(StateCollection stateCollection, TextMaker textMaker) {
        this.stateCollection = stateCollection;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.WelcomeCommands, connection.getLocale());
        connection.getOutputQueue().push(text);
        return stateCollection.get(StateName.WelcomeCommandsHandler);
    }
}
