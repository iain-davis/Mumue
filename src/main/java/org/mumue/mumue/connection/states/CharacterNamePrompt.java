package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CharacterNamePrompt implements ConnectionState {
    private final CharacterNamePromptHandler characterNamePromptHandler;
    private final TextMaker textMaker;

    @Inject
    @Singleton
    public CharacterNamePrompt(CharacterNamePromptHandler characterNamePromptHandler, TextMaker textMaker) {
        this.characterNamePromptHandler = characterNamePromptHandler;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.CharacterNamePrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return characterNamePromptHandler;
    }
}
