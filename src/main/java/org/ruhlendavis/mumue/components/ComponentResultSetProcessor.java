package org.ruhlendavis.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponentResultSetProcessor {
    private TimestampAbleResultSetProcessor timestampProcessor = new TimestampAbleResultSetProcessor();
    public void process(ResultSet resultSet, Component component) throws SQLException {
        component.setId(resultSet.getLong("id"));
        component.setName(resultSet.getString("name"));
        component.setDescription(resultSet.getString("description"));
        timestampProcessor.process(resultSet, component);
    }
}
