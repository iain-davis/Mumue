package org.mumue.mumue.interpreter;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseAccessorProvider;

import java.util.Collection;
import java.util.List;

class CommandEntryDao {
    private static final String GET_QUERY = "select * from commands;";
    private final DatabaseAccessor database;

    public CommandEntryDao(DatabaseAccessor database) {
        this.database = database;
    }

    public CommandEntryDao() {
        this(DatabaseAccessorProvider.get());
    }

    public Collection<CommandEntry> getCommands() {
        ResultSetHandler<List<CommandEntry>> resultSetHandler = new BeanListHandler<>(CommandEntry.class);
        return database.query(GET_QUERY, resultSetHandler);
    }
}
