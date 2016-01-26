package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Singleton;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
public class UniverseSelectionPrompt implements ConnectionState {
    private final ConnectionStateService connectionStateService;
    private final UniverseDao universeDao;
    private final TextMaker textMaker;

    @Inject
    public UniverseSelectionPrompt(ConnectionStateService connectionStateService, UniverseDao universeDao, TextMaker textMaker) {
        this.connectionStateService = connectionStateService;
        this.universeDao = universeDao;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        StringBuilder builder = new StringBuilder(GlobalConstants.TCP_LINE_SEPARATOR);
        for (Universe universe : universeDao.getUniverses()) {
            builder.append(universe.getId()).append(") ").append(universe.getName()).append(GlobalConstants.TCP_LINE_SEPARATOR);
        }
        connection.getOutputQueue().push(builder.toString());

        String text = textMaker.getText(TextName.UniverseSelectionPrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return connectionStateService.get(UniverseSelectionHandler.class);
    }
}
