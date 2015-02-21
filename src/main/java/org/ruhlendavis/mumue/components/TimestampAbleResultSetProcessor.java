package org.ruhlendavis.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimestampAbleResultSetProcessor {
    public void process(ResultSet resultSet, TimestampAble timestampAble) throws SQLException {
        timestampAble.setCreated(resultSet.getTimestamp("created").toInstant());
        timestampAble.setLastModified(resultSet.getTimestamp("lastUsed").toInstant());
        timestampAble.setLastUsed(resultSet.getTimestamp("lastUsed").toInstant());
        timestampAble.setUseCount(resultSet.getLong("useCount"));
    }
}
