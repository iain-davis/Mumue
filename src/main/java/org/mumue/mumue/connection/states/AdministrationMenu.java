package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Singleton;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
class AdministrationMenu implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final TextMaker textMaker;

    @Inject
    public AdministrationMenu(ConnectionStateProvider connectionStateProvider, TextMaker textMaker) {
        this.connectionStateProvider = connectionStateProvider;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.AdministrationMenu, connection.getLocale());
        connection.getOutputQueue().push(text);
        return connectionStateProvider.get(AdministrationMenuHandler.class);
    }
}
