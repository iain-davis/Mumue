package org.ruhlendavis.meta.database;

import javax.sql.DataSource;

import com.google.inject.AbstractModule;
import org.apache.commons.dbutils.QueryRunner;

public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataSource.class).toProvider(DataSourceProvider.class);
        bind(QueryRunner.class).toProvider(QueryRunnerProvider.class);
    }
}
