package org.ruhlendavis.meta.database;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public class QueryRunnerFactory {
    private static QueryRunner queryRunner;

    public QueryRunner createQueryRunner(DataSource dataSource) {
        if (queryRunner == null) {
            queryRunner = new QueryRunner(dataSource);
        }
        return queryRunner;
    }
}
