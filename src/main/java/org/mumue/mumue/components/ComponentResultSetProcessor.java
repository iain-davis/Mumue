package org.mumue.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponentResultSetProcessor {
    public void process(ResultSet resultSet, Component component) throws SQLException {
        component.setId(resultSet.getLong("id"));
        component.setCreated(resultSet.getTimestamp("created").toInstant());
        component.setLastModified(resultSet.getTimestamp("lastModified").toInstant());
        component.setLastUsed(resultSet.getTimestamp("lastUsed").toInstant());
        component.setUseCount(resultSet.getLong("useCount"));
    }
}
