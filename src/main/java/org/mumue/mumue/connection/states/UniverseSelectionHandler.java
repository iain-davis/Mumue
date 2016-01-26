package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class UniverseSelectionHandler implements ConnectionState {
    private final ConnectionStateService connectionStateService;
    private final TextMaker textMaker;
    private final UniverseDao universeDao;

    @Inject
    public UniverseSelectionHandler(ConnectionStateService connectionStateService, TextMaker textMaker, UniverseDao universeDao) {
        this.connectionStateService = connectionStateService;
        this.textMaker = textMaker;
        this.universeDao = universeDao;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }

        String selection = connection.getInputQueue().pop();
        long universeId = Long.parseLong(selection);

        Universe universe = universeDao.getUniverse(universeId);
        if (universe.getId() == GlobalConstants.REFERENCE_UNKNOWN) {
            return promptForUniverseAgain(connection);
        }

        connection.getCharacter().setUniverseId(universeId);
        return connectionStateService.get(CharacterNamePrompt.class);
    }

    private ConnectionState promptForUniverseAgain(Connection connection) {
        String text = textMaker.getText(TextName.InvalidOption, connection.getLocale());
        connection.getOutputQueue().push(text);
        return connectionStateService.get(UniverseSelectionPrompt.class);
    }
}
