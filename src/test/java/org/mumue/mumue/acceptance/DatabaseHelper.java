package org.mumue.mumue.acceptance;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.SqlConstants;

import javax.sql.DataSource;

public class DatabaseHelper {
    public static DatabaseAccessor setupTestDatabaseWithDefaultData() {
        DatabaseAccessor database = setupTestDatabaseWithSchema();
        database.update(SqlConstants.DEFAULT_DATA_SCRIPT);
        return database;
    }

    public static DatabaseAccessor setupTestDatabaseWithSchema() {
        DatabaseAccessor database = setupTestDatabaseWithoutSchema();
        database.update(SqlConstants.SCHEMA_SCRIPT);
        return database;
    }

    public static DatabaseAccessor setupTestDatabaseWithoutSchema() {
        DataSource source = setupDataSource();
        DatabaseAccessor database = new DatabaseAccessor(source);
        database.update(TestConstants.QUERY_PURGE_DATABASE);
        return database;
    }

    public static BasicDataSource setupDataSource() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(SqlConstants.DRIVER_CLASS_NAME);
        source.setUsername("user");
        source.setPassword("password");
        source.setUrl(TestConstants.MEMORY_DATABASE);
        return source;
    }
}
