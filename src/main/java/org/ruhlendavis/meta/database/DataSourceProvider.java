package org.ruhlendavis.meta.database;

import javax.sql.DataSource;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.commons.dbcp2.BasicDataSource;

import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;

public class DataSourceProvider implements Provider<DataSource> {
    private StartupConfiguration startupConfiguration;

    @Inject
    public DataSourceProvider(StartupConfiguration startupConfiguration) {
        this.startupConfiguration = startupConfiguration;
    }

    @Override
    public DataSource get() {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(SqlConstants.DRIVER_CLASS_NAME);
        source.setUsername(startupConfiguration.getDatabaseUsername());
        source.setPassword(startupConfiguration.getDatabasePassword());
        source.setUrl("jdbc:h2:" + startupConfiguration.getDatabasePath() + ";MV_STORE=FALSE;MVCC=FALSE");
        return source;
    }
}
