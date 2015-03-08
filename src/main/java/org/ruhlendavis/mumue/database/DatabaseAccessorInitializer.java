package org.ruhlendavis.mumue.database;

public class DatabaseAccessorInitializer {
    private DatabaseAccessorProvider provider = new DatabaseAccessorProvider();
    public void initialize() {
        provider.create(QueryRunnerProvider.get());
    }
}
