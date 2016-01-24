package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CharacterNamePrompt implements ConnectionState {
    private final WaitForCharacterName waitForCharacterName;
    private final TextMaker textMaker;

    @Inject
    public CharacterNamePrompt(WaitForCharacterName waitForCharacterName, TextMaker textMaker) {
        this.waitForCharacterName = waitForCharacterName;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.CharacterNamePrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return waitForCharacterName;
    }
}
