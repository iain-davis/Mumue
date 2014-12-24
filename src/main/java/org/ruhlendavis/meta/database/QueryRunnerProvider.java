package org.ruhlendavis.meta.database;

import javax.sql.DataSource;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.commons.dbutils.QueryRunner;

public class QueryRunnerProvider implements Provider<QueryRunner> {
    private DataSource source;

    @Inject
    public QueryRunnerProvider(DataSource source) {
        this.source = source;
    }

    @Override
    public QueryRunner get() {
        return new QueryRunner(source);
    }
}
