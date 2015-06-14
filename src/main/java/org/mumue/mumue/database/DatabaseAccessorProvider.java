package org.mumue.mumue.database;

import org.apache.commons.dbutils.QueryRunner;

public class DatabaseAccessorProvider {
    private static DatabaseAccessor databaseAccessor;

    public DatabaseAccessor create(QueryRunner queryRunner) {
        if (databaseAccessor == null) {
            databaseAccessor = new DatabaseAccessor(queryRunner);
        }
        return databaseAccessor;
    }

    public static DatabaseAccessor get() {
        return databaseAccessor;
    }
}
