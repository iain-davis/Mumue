package org.mumue.mumue.database;

import com.google.inject.AbstractModule;
import org.mumue.mumue.configuration.ComponentIdManager;
import org.mumue.mumue.testobjectbuilder.Nimue;

public class TestDatabaseModule extends AbstractModule {
    private final DatabaseAccessor databaseAccessor;

    public TestDatabaseModule(DatabaseAccessor database) {
        this.databaseAccessor = database;
    }

    @Override
    protected void configure() {
        bind(DatabaseAccessor.class).toInstance(databaseAccessor);
        bind(ComponentIdManager.class).toInstance(Nimue.componentIdManager());
    }
}
