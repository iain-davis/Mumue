package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class EnterUniverse implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String text = textMaker.getText(TextName.EnterUniverse, connection.getPlayer().getLocale(), configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return new EnterSpace();
    }
}
