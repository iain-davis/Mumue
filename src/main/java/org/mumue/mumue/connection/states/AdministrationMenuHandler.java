package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Singleton;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;
import org.mumue.mumue.text.TextQueue;

@Singleton
class AdministrationMenuHandler implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final TextMaker textMaker;

    @Inject
    public AdministrationMenuHandler(ConnectionStateProvider connectionStateProvider, TextMaker textMaker) {
        this.connectionStateProvider = connectionStateProvider;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        TextQueue inputQueue = connection.getInputQueue();
        if (inputQueue.isEmpty()) {
            return connectionStateProvider.get(AdministrationMenuHandler.class);
        }
        String selection = inputQueue.pop().toUpperCase();
        switch(selection) {
            case "I":
                return connectionStateProvider.get(ImportPathPrompt.class);
            case "E":
                return connectionStateProvider.get(PlayerMenuPrompt.class);
            default:
                connection.getOutputQueue().push(textMaker.getText(TextName.InvalidOption, connection.getLocale()));
                return connectionStateProvider.get(AdministrationMenu.class);
        }
    }
}
