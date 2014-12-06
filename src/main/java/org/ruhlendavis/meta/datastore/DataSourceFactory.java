package org.ruhlendavis.meta.datastore;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import org.ruhlendavis.meta.configuration.file.FileConfiguration;

public class DataSourceFactory {
    public DataSource createDataSource(FileConfiguration fileConfiguration) {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("org.h2.Driver");
        source.setUsername(fileConfiguration.getDatabaseUsername());
        source.setPassword(fileConfiguration.getDatabasePassword());
        source.setUrl("jdbc:h2:" + fileConfiguration.getDatabasePath() + ";MV_STORE=FALSE;MVCC=FALSE");
        return source;
    }
}
