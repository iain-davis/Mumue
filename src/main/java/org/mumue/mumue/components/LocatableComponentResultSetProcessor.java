package org.mumue.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocatableComponentResultSetProcessor {
    private final GameComponentResultSetProcessor gameComponentResultSetProcessor;

    public LocatableComponentResultSetProcessor() {
        this(new GameComponentResultSetProcessor());
    }

    LocatableComponentResultSetProcessor(GameComponentResultSetProcessor gameComponentResultSetProcessor) {
        this.gameComponentResultSetProcessor = gameComponentResultSetProcessor;
    }

    public void process(ResultSet resultSet, LocatableComponent component) throws SQLException {
        component.setLocationId(resultSet.getLong("locationId"));
        component.setUniverseId(resultSet.getLong("universeId"));
        gameComponentResultSetProcessor.process(resultSet, component);
    }
}
