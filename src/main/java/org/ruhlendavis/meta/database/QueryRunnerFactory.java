package org.ruhlendavis.meta.database;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public class QueryRunnerFactory {
    public QueryRunner create(DataSource source) {
        return new QueryRunner(source);
    }
}
