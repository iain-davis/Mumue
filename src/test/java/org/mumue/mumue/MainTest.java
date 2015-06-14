package org.mumue.mumue;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class MainTest {
//    @Mock Configuration configuration;
//    @Mock InfiniteLoopRunner acceptorLoop;
//
//    @Mock ConfigurationInitializer configurationInitializer;
//    @Mock QueryRunnerInitializer queryRunnerInitializer;
//    @Mock DatabaseInitializer databaseInitializer;
//    @Mock DatabaseAccessorInitializer databaseAccessorInitializer;
//    @Mock AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder;
//
//    @InjectMocks Main main;
//
//    @Before
//    public void beforeEach() throws URISyntaxException {
//        when(configurationInitializer.initialize(anyVararg())).thenReturn(configuration);
//        when(configuration.isTest()).thenReturn(true);
//        when(acceptorLoopRunnerBuilder.build(eq(configuration), any(ConnectionManager.class))).thenReturn(acceptorLoop);
//        when(acceptorLoop.isRunning()).thenReturn(true);
//    }
//
//    @Test
//    public void initializeConfiguration() {
//        main.run();
//        verify(configurationInitializer).initialize();
//    }
//
//    @Test
//    public void initializeQueryRunner() {
//        main.run();
//        verify(queryRunnerInitializer).initialize(configuration);
//    }
//
//    @Test
//    public void initializeDatabaseAccessor() {
//        main.run();
//        verify(databaseAccessorInitializer).initialize();
//    }
//
//    @Test
//    public void initializeDatabase() {
//        main.run();
//        verify(databaseInitializer).initialize();
//    }
//
//    @Test
//    public void buildConnectionAcceptorLoopRunner() {
//        main.run();
//        verify(acceptorLoopRunnerBuilder).build(eq(configuration), any(ConnectionManager.class));
//    }
}
