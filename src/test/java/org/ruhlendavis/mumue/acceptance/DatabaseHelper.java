package org.ruhlendavis.mumue.acceptance;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

import org.ruhlendavis.mumue.database.DatabaseAccessor;
import org.ruhlendavis.mumue.database.DatabaseAccessorProvider;
import org.ruhlendavis.mumue.database.QueryRunnerProvider;
import org.ruhlendavis.mumue.database.SqlConstants;

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
        DataSource source = setupDataSource();
        QueryRunner queryRunner = new QueryRunnerProvider().create(source);
        DatabaseAccessor database = new DatabaseAccessorProvider().create(queryRunner);
        database.update(TestConstants.QUERY_PURGE_DATABASE);
        return queryRunner;
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
