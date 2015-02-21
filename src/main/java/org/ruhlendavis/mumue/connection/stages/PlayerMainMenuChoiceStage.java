package org.ruhlendavis.mumue.connection.stages;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class PlayerMainMenuChoiceStage implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        String input = connection.getInputQueue().pop();
        switch (input.toUpperCase()) {
            case "P":
                return new PlayCharacterStage();
            case "C":
                return new CharacterNamePromptStage();
            default:
                String locale = connection.getPlayer().getLocale();
                String serverLocale = configuration.getServerLocale();
                String text = textMaker.getText(TextName.InvalidOption, locale, serverLocale);
                connection.getOutputQueue().push(text);
                return new DisplayPlayerMenuStage();
        }
    }
}
