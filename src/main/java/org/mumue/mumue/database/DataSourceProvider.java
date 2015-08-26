package org.mumue.mumue.database;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.sql.DataSource;

public class DataSourceProvider implements Provider<DataSource> {
    private final DatabaseConfiguration configuration;

    @Inject
    public DataSourceProvider(DatabaseConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public DataSource get() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(configuration.getDriverName());
        source.setUsername(configuration.getUsername());
        source.setPassword(configuration.getPassword());
        source.setUrl(configuration.getUrl());
        return source;
    }
}

