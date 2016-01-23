package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.login.WelcomeCommandsDisplay;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class DisplayWelcome implements ConnectionState {
    private final StateCollection stateCollection;
    private final Injector injector;
    private final TextMaker textMaker;

    @Inject
    public DisplayWelcome(StateCollection stateCollection, Injector injector, TextMaker textMaker) {
        this.stateCollection = stateCollection;
        this.injector = injector;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.Welcome, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        if (connection.getPortConfiguration().isSupportsMenus()) {
            return stateCollection.get(StateName.DisplayLoginPrompt);
        } else {
            return injector.getInstance(WelcomeCommandsDisplay.class);
        }
    }
}
