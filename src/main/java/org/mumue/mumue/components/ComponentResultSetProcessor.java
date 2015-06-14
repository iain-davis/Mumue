package org.mumue.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponentResultSetProcessor {
    private ComponentBaseResultSetProcessor componentBaseProcessor = new ComponentBaseResultSetProcessor();

    public void process(ResultSet resultSet, Component component) throws SQLException {
        component.setName(resultSet.getString("name"));
        component.setDescription(resultSet.getString("description"));
        componentBaseProcessor.process(resultSet, component);
    }
}
