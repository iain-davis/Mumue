package org.ruhlendavis.meta;

import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationInitializer;
import org.ruhlendavis.meta.database.DatabaseInitializer;
import org.ruhlendavis.meta.database.QueryRunnerInitializer;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    @Mock Configuration configuration;

    @Mock ConfigurationInitializer configurationInitializer;
    @Mock QueryRunnerInitializer queryRunnerInitializer;
    @Mock DatabaseInitializer databaseInitializer;

    @InjectMocks Main main;

    @Before
    public void beforeEach() throws URISyntaxException {
        when(configurationInitializer.initialize(anyVararg())).thenReturn(configuration);
        when(configuration.isTest()).thenReturn(true);
    }

    @Test
    public void doNotRunForeverInTest() {
        Main.main("--test");
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
