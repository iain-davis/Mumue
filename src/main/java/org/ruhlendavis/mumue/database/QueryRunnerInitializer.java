package org.ruhlendavis.mumue.database;

import javax.sql.DataSource;

import org.ruhlendavis.mumue.configuration.Configuration;

public class QueryRunnerInitializer {
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();
    private QueryRunnerProvider queryRunnerProvider = new QueryRunnerProvider();

    public void initialize(Configuration configuration) {
        DataSource dataSource = dataSourceFactory.create(configuration);
        queryRunnerProvider.create(dataSource);
    }
}
