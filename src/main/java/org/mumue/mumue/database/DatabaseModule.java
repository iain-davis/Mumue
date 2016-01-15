package org.mumue.mumue.database;

import javax.inject.Singleton;
import javax.sql.DataSource;

import com.google.inject.AbstractModule;

public class DatabaseModule extends AbstractModule {
    private final DatabaseConfiguration configuration;

    public DatabaseModule(DatabaseConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(DatabaseConfiguration.class).toInstance(configuration);
        bind(DataSource.class).toProvider(DataSourceProvider.class).in(Singleton.class);
    }
}
