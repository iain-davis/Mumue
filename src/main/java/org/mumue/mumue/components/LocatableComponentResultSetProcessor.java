package org.mumue.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocatableComponentResultSetProcessor {
    private NameableComponentResultSetProcessor nameableProcessor = new NameableComponentResultSetProcessor();

    public void process(ResultSet resultSet, LocatableComponent component) throws SQLException {
        component.setLocationId(resultSet.getLong("locationId"));
        component.setUniverseId(resultSet.getLong("universeId"));
        nameableProcessor.process(resultSet, component);
    }
}
