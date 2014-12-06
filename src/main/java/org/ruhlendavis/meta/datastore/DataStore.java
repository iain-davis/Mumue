package org.ruhlendavis.meta.datastore;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.ruhlendavis.meta.configuration.file.FileConfiguration;

public class DataStore {
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();
    private QueryRunnerFactory queryRunnerFactory = new QueryRunnerFactory();

    public boolean isDatabaseEmpty(FileConfiguration fileConfiguration) {
        QueryRunner queryRunner = getQueryRunner(getDataSource(fileConfiguration));
        ResultSetHandler rsh = new ScalarHandler<>(1);
        try {
            long found = (long) queryRunner.query(SqlConstants.CHECK_CONFIGURATION_TABLE_EXISTENCE, rsh);
            return found == 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public void populateDatabase(FileConfiguration fileConfiguration) {
        QueryRunner queryRunner = getQueryRunner(getDataSource(fileConfiguration));
        try {
            queryRunner.update(SqlConstants.SCHEMA_SCRIPT);
            queryRunner.update(SqlConstants.DEFAULT_DATA_SCRIPT);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private DataSource getDataSource(FileConfiguration fileConfiguration) {
        return dataSourceFactory.createDataSource(fileConfiguration);
    }

    private QueryRunner getQueryRunner(DataSource dataSource) {
        return queryRunnerFactory.createQueryRunner(dataSource);
    }

    public String getText(FileConfiguration fileConfiguration, String locale, String name) {
        String text = "";
        QueryRunner queryRunner = getQueryRunner(getDataSource(fileConfiguration));
        ResultSetHandler rsh = new ScalarHandler<>(SqlConstants.TEXT_COLUMN);
        try {
            text = (String)queryRunner.query(SqlConstants.QUERY_TEXT, rsh, locale, name);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return text;
    }

    public String getText(FileConfiguration fileConfiguration, String key) {
        return getText(fileConfiguration, getDefaultLocale(), key);
    }

    private String getDefaultLocale() {
        return null;
    }
}
