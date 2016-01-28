package org.mumue.mumue.connection.states;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;
import org.mumue.mumue.text.TextQueue;

class AdministrationMenuHandler implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final TextMaker textMaker;

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
        if (inputQueue.pop().equalsIgnoreCase("I")) {
            return connectionStateProvider.get(ImportPathPrompt.class);
        }
        connection.getOutputQueue().push(textMaker.getText(TextName.InvalidOption, connection.getLocale()));
        return connectionStateProvider.get(AdministrationMenu.class);
    }
}
