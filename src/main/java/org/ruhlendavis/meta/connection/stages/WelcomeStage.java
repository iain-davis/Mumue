package org.ruhlendavis.meta.connection.stages;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.TextQueue;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

public class WelcomeStage implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(TextQueue inputQueue, TextQueue outputQueue, Configuration configuration) {
        String text = textMaker.getText(configuration.getServerLocale(), TextName.Welcome);
        outputQueue.push(text);
        return new LoginPromptStage();
    }
}
