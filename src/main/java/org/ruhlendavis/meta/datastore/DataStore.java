package org.ruhlendavis.meta.datastore;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.ruhlendavis.meta.configuration.Configuration;

public class DataStore {
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();
    private QueryRunnerFactory queryRunnerFactory = new QueryRunnerFactory();

    public boolean isDatabaseEmpty(Configuration configuration) {
        QueryRunner queryRunner = getQueryRunner(getDataSource(configuration));
        ResultSetHandler rsh = new ScalarHandler<>(1);
        try {
            long found = (long) queryRunner.query(SqlConstants.CHECK_CONFIGURATION_TABLE_EXISTENCE, rsh);
            return found == 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public void populateDatabase(Configuration configuration) {
        QueryRunner queryRunner = getQueryRunner(getDataSource(configuration));
        try {
            queryRunner.update(SqlConstants.SCHEMA_SCRIPT);
            queryRunner.update(SqlConstants.DEFAULT_DATA_SCRIPT);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private DataSource getDataSource(Configuration configuration) {
            return dataSourceFactory.createDataSource(configuration);
    }

    private QueryRunner getQueryRunner(DataSource dataSource) {
            return queryRunnerFactory.createQueryRunner(dataSource);
    }
}
