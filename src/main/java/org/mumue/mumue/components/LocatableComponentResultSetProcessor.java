package org.mumue.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocatableComponentResultSetProcessor {
    private GameComponentResultSetProcessor nameableProcessor = new GameComponentResultSetProcessor();

    public void process(ResultSet resultSet, LocatableComponent component) throws SQLException {
        component.setLocationId(resultSet.getLong("locationId"));
        component.setUniverseId(resultSet.getLong("universeId"));
        nameableProcessor.process(resultSet, component);
    }
}
