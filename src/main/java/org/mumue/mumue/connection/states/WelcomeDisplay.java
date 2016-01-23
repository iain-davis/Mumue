package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WelcomeDisplay implements ConnectionState {
    private final StateCollection stateCollection;
    private final TextMaker textMaker;

    @Inject
    @Singleton
    public WelcomeDisplay(StateCollection stateCollection, TextMaker textMaker) {
        this.stateCollection = stateCollection;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.Welcome, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        if (connection.getPortConfiguration().isSupportsMenus()) {
            return stateCollection.get(StateName.LoginIdPrompt);
        } else {
            return stateCollection.get(StateName.WelcomeCommandsDisplay);
        }
    }
}
