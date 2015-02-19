package org.ruhlendavis.mumue.database;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

public class DatabaseInitializerDao {
    public boolean hasSchema() {
        QueryRunner queryRunner = QueryRunnerProvider.get();
        ResultSetHandler resultSetHandler = new ScalarHandler<>(1);
        try {
            long found = (long) queryRunner.query(SqlConstants.CHECK_CONFIGURATION_TABLE_EXISTENCE, resultSetHandler);
            return found != 0;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public boolean hasData() {
        QueryRunner queryRunner = QueryRunnerProvider.get();
        ResultSetHandler resultSetHandler = new ScalarHandler<>(1);
        try {
            String version = (String) queryRunner.query(SqlConstants.CHECK_CONFIGURATION_TABLE_VERSION, resultSetHandler);
            return StringUtils.isNotBlank(version);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void loadSchema() {
        QueryRunner queryRunner = QueryRunnerProvider.get();
        try {
            queryRunner.update(SqlConstants.SCHEMA_SCRIPT);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void loadDefaultData() {
        QueryRunner queryRunner = QueryRunnerProvider.get();
        try {
            queryRunner.update(SqlConstants.DEFAULT_DATA_SCRIPT);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
