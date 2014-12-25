package org.ruhlendavis.meta.database;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

public class QueryRunnerProvider {
    private static QueryRunner queryRunner;

    public static QueryRunner create(DataSource source) {
        if (queryRunner == null) {
            queryRunner = new QueryRunner(source);
        }
        return queryRunner;
    }

    public static QueryRunner get() {
        return queryRunner;
    }
}
