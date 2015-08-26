package org.mumue.mumue.database;

import javax.inject.Inject;

public class DatabaseAccessorProvider {
    private static DatabaseAccessor databaseAccessor;

    @Inject
    public DatabaseAccessorProvider(DatabaseAccessor databaseAccessor) {
        DatabaseAccessorProvider.databaseAccessor = databaseAccessor;
    }

    public static DatabaseAccessor get() {
        return databaseAccessor;
    }
}
