package org.mumue.mumue.connection.stages.mainmenu;

import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

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
