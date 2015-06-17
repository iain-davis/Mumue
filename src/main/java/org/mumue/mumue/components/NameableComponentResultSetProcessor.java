package org.mumue.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NameableComponentResultSetProcessor {
    private ComponentResultSetProcessor componentProcessor = new ComponentResultSetProcessor();

    public void process(ResultSet resultSet, NameableComponent component) throws SQLException {
        component.setName(resultSet.getString("name"));
        component.setDescription(resultSet.getString("description"));
        componentProcessor.process(resultSet, component);
    }
}
