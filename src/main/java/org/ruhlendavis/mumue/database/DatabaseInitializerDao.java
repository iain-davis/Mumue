package org.ruhlendavis.mumue.database;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

public class DatabaseInitializerDao {
    public boolean hasSchema() {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler resultSetHandler = new ScalarHandler<>(1);
        long found = (long) database.query(SqlConstants.CHECK_CONFIGURATION_TABLE_EXISTENCE, resultSetHandler);
        return found != 0;
    }

    public boolean hasData() {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<String> resultSetHandler = new ScalarHandler<>(1);
        String version = database.query(SqlConstants.CHECK_CONFIGURATION_TABLE_VERSION, resultSetHandler);
        return StringUtils.isNotBlank(version);
    }

    public void loadSchema() {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        database.update(SqlConstants.SCHEMA_SCRIPT);
    }

    public void loadDefaultData() {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        database.update(SqlConstants.DEFAULT_DATA_SCRIPT);
    }
}
