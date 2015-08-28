package org.mumue.mumue.database;

import javax.sql.DataSource;

public class DatabaseHelper {
    private static final String MEMORY_DATABASE = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String QUERY_PURGE_DATABASE = "DROP ALL OBJECTS";

    public static DatabaseAccessor setupTestDatabaseWithDefaultData() {
        DatabaseAccessor database = setupTestDatabaseWithSchema();
        database.update(DatabaseInitializerDao.DEFAULT_DATA_SCRIPT);
        return database;
    }

    public static DatabaseAccessor setupTestDatabaseWithSchema() {
        DatabaseAccessor database = setupTestDatabaseWithoutSchema();
        database.update(DatabaseInitializerDao.SCHEMA_SCRIPT);
        return database;
    }

    public static DatabaseAccessor setupTestDatabaseWithoutSchema() {
        DataSource source = new DataSourceProvider(createDatabaseConfiguration()).get();
        DatabaseAccessor database = new DatabaseAccessor(source);
        database.update(QUERY_PURGE_DATABASE);
        return database;
    }

    private static DatabaseConfiguration createDatabaseConfiguration() {
        return new DatabaseConfigurationBuilder()
                .with(DatabaseConfiguration.DATABASE_URL, MEMORY_DATABASE)
                .build();
    }
}
