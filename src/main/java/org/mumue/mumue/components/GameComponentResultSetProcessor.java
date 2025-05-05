package org.mumue.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameComponentResultSetProcessor {
    private final ComponentResultSetProcessor componentProcessor;

    public GameComponentResultSetProcessor() {
        this(new ComponentResultSetProcessor());
    }

    GameComponentResultSetProcessor(ComponentResultSetProcessor componentProcessor) {
        this.componentProcessor = componentProcessor;
    }

    public void process(ResultSet resultSet, GameComponent component) throws SQLException {
        component.setName(resultSet.getString("name"));
        component.setDescription(resultSet.getString("description"));
        componentProcessor.process(resultSet, component);
    }
}
