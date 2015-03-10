package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.components.universe.Universe;
import org.ruhlendavis.mumue.components.universe.UniverseDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class UniverseSelectionPrompt implements ConnectionStage {
    private UniverseDao dao = new UniverseDao();
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        StringBuilder builder = new StringBuilder("\\r\\n");
        for (Universe universe : dao.getUniverses()) {
            builder.append(universe.getId()).append(") ").append(universe.getName()).append("\\r\\n");
        }
        connection.getOutputQueue().push(builder.toString());

        String text = textMaker.getText(TextName.UniverseSelectionPrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return new WaitForUniverseSelection();
    }
}
