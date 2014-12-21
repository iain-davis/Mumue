package org.ruhlendavis.meta.datastore;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.database.DataSourceFactory;
import org.ruhlendavis.meta.database.QueryRunnerFactory;
import org.ruhlendavis.meta.database.SqlConstants;

public class DataStore {
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();
    private QueryRunnerFactory queryRunnerFactory = new QueryRunnerFactory();

    private DataSource getDataSource(StartupConfiguration startupConfiguration) {
        return dataSourceFactory.createDataSource(startupConfiguration);
    }

    private QueryRunner getQueryRunner(DataSource dataSource) {
        return queryRunnerFactory.createQueryRunner(dataSource);
    }

    public String getText(StartupConfiguration startupConfiguration, String locale, String name) {
        String text = "";
        QueryRunner queryRunner = getQueryRunner(getDataSource(startupConfiguration));
        ResultSetHandler rsh = new ScalarHandler<>(SqlConstants.TEXT_COLUMN);
        try {
            text = (String)queryRunner.query(SqlConstants.QUERY_TEXT, rsh, locale, name);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return text;
    }
}
