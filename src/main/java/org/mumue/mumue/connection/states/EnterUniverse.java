package org.mumue.mumue.connection.states;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
public class EnterUniverse implements ConnectionState {
    private final ConnectionStateService connectionStateService;
    private final TextMaker textMaker;
    private final UniverseDao dao;

    @Inject
    public EnterUniverse(ConnectionStateService connectionStateService, TextMaker textMaker, UniverseDao dao) {
        this.connectionStateService = connectionStateService;
        this.textMaker = textMaker;
        this.dao = dao;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        Universe universe = dao.getUniverse(connection.getCharacter().getUniverseId());
        Map<String, String> variables = new HashMap<>();
        variables.put("universe name", universe.getName());
        String text = textMaker.getText(TextName.EnterUniverse, connection.getLocale(), variables);
        connection.getOutputQueue().push(text);
        return connectionStateService.get(EnterSpace.class);
    }
}
