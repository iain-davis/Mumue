package org.ruhlendavis.meta.database;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import org.ruhlendavis.meta.configuration.Configuration;

public class DataSourceFactory {
    public DataSource create(Configuration configuration) {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(SqlConstants.DRIVER_CLASS_NAME);
        source.setUsername(configuration.getDatabaseUsername());
        source.setPassword(configuration.getDatabasePassword());
        source.setUrl("jdbc:h2:" + configuration.getDatabasePath() + ";MV_STORE=FALSE;MVCC=FALSE");
        return source;
    }
}
