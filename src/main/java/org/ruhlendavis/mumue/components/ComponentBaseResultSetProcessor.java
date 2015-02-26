package org.ruhlendavis.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponentBaseResultSetProcessor {
    public void process(ResultSet resultSet, ComponentBase componentBase) throws SQLException {
        componentBase.setId(resultSet.getLong("id"));
        componentBase.setCreated(resultSet.getTimestamp("created").toInstant());
        componentBase.setLastModified(resultSet.getTimestamp("lastUsed").toInstant());
        componentBase.setLastUsed(resultSet.getTimestamp("lastUsed").toInstant());
        componentBase.setUseCount(resultSet.getLong("useCount"));
    }
}
