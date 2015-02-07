package org.ruhlendavis.meta;

import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.acceptance.DatabaseHelper;
import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationInitializer;
import org.ruhlendavis.meta.database.DataSourceFactory;
import org.ruhlendavis.meta.database.DatabaseInitializer;
import org.ruhlendavis.meta.database.QueryRunnerInitializer;
import org.ruhlendavis.meta.database.QueryRunnerProvider;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    @Mock Configuration configuration;

    @Mock ConfigurationInitializer configurationInitializer;
    @Mock QueryRunnerInitializer queryRunnerInitializer;
    @Mock DatabaseInitializer databaseInitializer;

    @Mock DataSourceFactory dataSourceFactory;
    @Mock QueryRunnerProvider queryRunnerProvider;
    @Mock Listener listener;

    @InjectMocks Main main;

    private DataSource dataSource = DatabaseHelper.setupDataSource();

    @Before
    public void beforeEach() throws URISyntaxException {
        when(configurationInitializer.initialize(anyVararg())).thenReturn(configuration);
        when(configuration.isTest()).thenReturn(true);

        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithoutSchema();
        when(dataSourceFactory.create(configuration)).thenReturn(dataSource);
        when(queryRunnerProvider.create(dataSource)).thenReturn(queryRunner);
    }

    @Test
    public void doNotRunForeverInTest() {
        main.run("--test");
    }

    @Test
    public void initializeConfiguration() {
        main.run();
        verify(configurationInitializer).initialize();
    }

    @Test
    public void initializeQueryRunner() {
        main.run();
        verify(queryRunnerInitializer).initialize(configuration);
    }

    @Test
    public void initializeDatabase() {
        main.run();
        verify(databaseInitializer).initialize();
    }
}
