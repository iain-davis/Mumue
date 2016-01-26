package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
public class CharacterNamePrompt implements ConnectionState {
    private final ConnectionStateService connectionStateService;
    private final TextMaker textMaker;

    @Inject
    public CharacterNamePrompt(ConnectionStateService connectionStateService, TextMaker textMaker) {
        this.connectionStateService = connectionStateService;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.CharacterNamePrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return connectionStateService.get(CharacterNameHandler.class);
    }
}
