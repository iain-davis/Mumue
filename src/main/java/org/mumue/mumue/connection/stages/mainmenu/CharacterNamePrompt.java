package org.mumue.mumue.connection.stages.mainmenu;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import javax.inject.Inject;

public class CharacterNamePrompt implements ConnectionStage {
    private final Injector injector;
    private final TextMaker textMaker;

    @Inject
    public CharacterNamePrompt(Injector injector, TextMaker textMaker) {
        this.injector = injector;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String text = textMaker.getText(TextName.CharacterNamePrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return injector.getInstance(WaitForCharacterName.class);
    }
}
