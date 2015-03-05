package org.ruhlendavis.mumue;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.configuration.ConfigurationInitializer;
import org.ruhlendavis.mumue.connection.AcceptorLoopRunnerBuilder;
import org.ruhlendavis.mumue.connection.ConnectionManager;
import org.ruhlendavis.mumue.database.DatabaseInitializer;
import org.ruhlendavis.mumue.database.QueryRunnerInitializer;
import org.ruhlendavis.mumue.runner.InfiniteLoopRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    @Mock Configuration configuration;
    @Mock InfiniteLoopRunner acceptorLoop;

    @Mock ConfigurationInitializer configurationInitializer;
    @Mock QueryRunnerInitializer queryRunnerInitializer;
    @Mock DatabaseInitializer databaseInitializer;
    @Mock AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder;

    @InjectMocks Main main;

    @Before
    public void beforeEach() throws URISyntaxException {
        when(configurationInitializer.initialize(anyVararg())).thenReturn(configuration);
        when(configuration.isTest()).thenReturn(true);
        when(acceptorLoopRunnerBuilder.build(eq(configuration), any(ConnectionManager.class))).thenReturn(acceptorLoop);
        when(acceptorLoop.isRunning()).thenReturn(true);
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

    @Test
    public void buildConnectionAcceptorLoopRunner() {
        main.run();
        verify(acceptorLoopRunnerBuilder).build(eq(configuration), any(ConnectionManager.class));
    }
}
