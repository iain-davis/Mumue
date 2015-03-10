package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.components.universe.Universe;
import org.ruhlendavis.mumue.components.universe.UniverseDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.importer.GlobalConstants;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class WaitForUniverseSelection implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    private UniverseDao dao = new UniverseDao();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }

        String selection = connection.getInputQueue().pop();
        long universeId = Long.parseLong(selection);

        Universe universe = dao.getUniverse(universeId);
        if (universe.getId() == GlobalConstants.REFERENCE_UNKNOWN) {
            return promptForUniverseAgain(connection);
        }

        connection.getCharacter().setUniverseId(universeId);
        return new CharacterNamePrompt();
    }

    private ConnectionStage promptForUniverseAgain(Connection connection) {
        String text = textMaker.getText(TextName.InvalidOption, connection.getLocale());
        connection.getOutputQueue().push(text);
        return new UniverseSelectionPrompt();
    }
}
