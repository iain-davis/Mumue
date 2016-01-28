package org.mumue.mumue.connection.states;

import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
class ImportPathPrompt implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final TextMaker textMaker;

    public ImportPathPrompt(ConnectionStateProvider connectionStateProvider, TextMaker textMaker) {
        this.connectionStateProvider = connectionStateProvider;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        connection.getOutputQueue().push(textMaker.getText(TextName.ImportFilePathPrompt, connection.getLocale()));
        return connectionStateProvider.get(ImportPathPromptHandler.class);
    }
}
