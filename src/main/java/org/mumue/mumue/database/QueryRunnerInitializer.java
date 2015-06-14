package org.mumue.mumue.database;

import javax.sql.DataSource;

import org.mumue.mumue.configuration.Configuration;

public class QueryRunnerInitializer {
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();
    private QueryRunnerProvider queryRunnerProvider = new QueryRunnerProvider();

    public void initialize(Configuration configuration) {
        DataSource dataSource = dataSourceFactory.create(configuration);
        queryRunnerProvider.create(dataSource);
    }
}
