package org.mumue.mumue.database;

import static org.junit.Assert.assertSame;

import java.util.Properties;
import javax.sql.DataSource;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

public class DatabaseModuleTest {
    private final DatabaseConfiguration configuration = new DatabaseConfiguration(new Properties());
    private final DatabaseModule module = new DatabaseModule(configuration);

    @Test
    public void instantiateDatabaseConfiguration() {
        Injector injector = Guice.createInjector(module);
        injector.getInstance(DatabaseConfiguration.class);
    }

    @Test
    public void instantiateDataSource() {
        Injector injector = Guice.createInjector(module);
        injector.getInstance(DataSource.class);
    }

    @Test
    public void instantiateDataSourceAsSingleton() {
        Injector injector = Guice.createInjector(module);
        assertSame(injector.getInstance(DataSource.class), injector.getInstance(DataSource.class));
    }
}