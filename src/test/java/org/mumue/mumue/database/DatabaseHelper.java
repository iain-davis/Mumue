package org.mumue.mumue.database;

import java.util.Random;
import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;

public class DatabaseHelper {
    static final String MEMORY_DATABASE = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String QUERY_PURGE_DATABASE = "DROP ALL OBJECTS";
    public static final Random RANDOM = new Random();

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

    public static long insertPlayer(DatabaseAccessor  database) {
        return insertPlayer(database, RANDOM.nextInt(1000000));
    }

    public static long insertPlayer(DatabaseAccessor database, long id) {
        return insertPlayer(database, id, RandomStringUtils.randomAlphabetic(16), RandomStringUtils.randomAlphabetic(13));
    }

    public static long insertPlayer(DatabaseAccessor database, String loginId, String password) {
        return insertPlayer(database, RANDOM.nextInt(10000000), loginId, password);
    }

    private static long insertPlayer(DatabaseAccessor database, long id, String loginId, String password) {
        return insertPlayer(database, id, loginId, password, RandomStringUtils.randomAlphabetic(5));
    }

    private static long insertPlayer(DatabaseAccessor database, long id, String loginId, String password, String locale) {
        String sql = "insert into players (id, loginId, password, locale, created, lastModified, lastUsed, useCount)"
                + " values (" + id + ", '" + loginId + "','" + password + "', '" + locale + "', "
                + "timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', timestamp '2014-06-12 21:30:00', 0);";
        database.update(sql);
        return id;
    }
}
