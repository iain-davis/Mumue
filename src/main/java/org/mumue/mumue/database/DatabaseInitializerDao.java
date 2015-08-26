package org.mumue.mumue.database;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

public class DatabaseInitializerDao {
    private final DatabaseAccessor database;

    public DatabaseInitializerDao(DatabaseAccessor database) {
        this.database = database;
    }

    public DatabaseInitializerDao() {
        this(DatabaseAccessorProvider.get());
    }

    public boolean hasSchema() {
        ResultSetHandler<Long> resultSetHandler = new ScalarHandler<>(1);
        long found = database.query(SqlConstants.CHECK_CONFIGURATION_TABLE_EXISTENCE, resultSetHandler);
        return found != 0;
    }

    public boolean hasData() {
        ResultSetHandler<String> resultSetHandler = new ScalarHandler<>(1);
        String version = database.query(SqlConstants.CHECK_CONFIGURATION_TABLE_VERSION, resultSetHandler);
        return StringUtils.isNotBlank(version);
    }

    public void loadSchema() {
        database.update(SqlConstants.SCHEMA_SCRIPT);
    }

    public void loadDefaultData() {
        database.update(SqlConstants.DEFAULT_DATA_SCRIPT);
    }
}
