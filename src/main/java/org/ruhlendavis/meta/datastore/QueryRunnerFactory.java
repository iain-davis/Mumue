package org.ruhlendavis.meta.datastore;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public class QueryRunnerFactory {
    public QueryRunner createQueryRunner(DataSource dataSource) {
        return new QueryRunner(dataSource);
    }
}
