package org.ruhlendavis.meta.acceptance;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

import org.ruhlendavis.meta.configuration.TestConstants;
import org.ruhlendavis.meta.database.QueryRunnerProvider;
import org.ruhlendavis.meta.database.SqlConstants;

public class DatabaseHelper {
    public static QueryRunner setupTestDatabaseWithDefaultData() {
        QueryRunner queryRunner = setupTestDatabaseWithSchema();
        try {
            queryRunner.update(SqlConstants.DEFAULT_DATA_SCRIPT);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return queryRunner;
    }

    public static QueryRunner setupTestDatabaseWithSchema() {
        QueryRunner queryRunner = setupTestDatabaseWithoutSchema();
        try {
            queryRunner.update(SqlConstants.SCHEMA_SCRIPT);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return queryRunner;
    }

    public static QueryRunner setupTestDatabaseWithoutSchema() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(SqlConstants.DRIVER_CLASS_NAME);
        source.setUsername("user");
        source.setPassword("password");
        source.setUrl(TestConstants.MEMORY_DATABASE);
        QueryRunnerProvider queryRunnerProvider = new QueryRunnerProvider(source);
        QueryRunner queryRunner = queryRunnerProvider.get();
        try {
            queryRunner.update(TestConstants.QUERY_PURGE_DATABASE);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return queryRunner;
    }
}
