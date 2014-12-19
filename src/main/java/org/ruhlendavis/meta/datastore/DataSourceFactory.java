package org.ruhlendavis.meta.datastore;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;

public class DataSourceFactory {
    public DataSource createDataSource(StartupConfiguration startupConfiguration) {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("org.h2.Driver");
        source.setUsername(startupConfiguration.getDatabaseUsername());
        source.setPassword(startupConfiguration.getDatabasePassword());
        source.setUrl("jdbc:h2:" + startupConfiguration.getDatabasePath() + ";MV_STORE=FALSE;MVCC=FALSE");
        return source;
    }
}
