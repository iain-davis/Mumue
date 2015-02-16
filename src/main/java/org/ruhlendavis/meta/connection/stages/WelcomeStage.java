package org.ruhlendavis.meta.connection.stages;

import java.util.Collection;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

public class WelcomeStage implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Collection<String> inputQueue, Collection<String> outputQueue, Configuration configuration) {
        String text = textMaker.getText(configuration.getServerLocale(), TextName.Welcome);
        outputQueue.add(text);
        return new NoOperationStage();
    }
}
