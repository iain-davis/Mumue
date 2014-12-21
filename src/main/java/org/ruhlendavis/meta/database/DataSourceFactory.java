package org.ruhlendavis.meta.database;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;

public class DataSourceFactory {
    public DataSource create(StartupConfiguration startupConfiguration) {
        return createDataSourceFor(startupConfiguration);
    }

    private BasicDataSource createDataSourceFor(StartupConfiguration startupConfiguration) {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(SqlConstants.DRIVER_CLASS_NAME);
        source.setUsername(startupConfiguration.getDatabaseUsername());
        source.setPassword(startupConfiguration.getDatabasePassword());
        source.setUrl("jdbc:h2:" + startupConfiguration.getDatabasePath() + ";MV_STORE=FALSE;MVCC=FALSE");
        return source;
    }
}
