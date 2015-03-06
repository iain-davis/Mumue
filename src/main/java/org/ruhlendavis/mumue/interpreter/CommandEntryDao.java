package org.ruhlendavis.mumue.interpreter;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.ruhlendavis.mumue.database.QueryRunnerProvider;

class CommandEntryDao {
    private static final String GET_QUERY = "select * from commands;";

    public Collection<CommandEntry> getCommands() {
        QueryRunner database = QueryRunnerProvider.get();
        try {
            ResultSetHandler<List<CommandEntry>> resultSetHandler = new BeanListHandler<>(CommandEntry.class);
            return database.query(GET_QUERY, resultSetHandler);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
