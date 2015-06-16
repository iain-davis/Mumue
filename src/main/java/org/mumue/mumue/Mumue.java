package org.mumue.mumue;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.configuration.ConfigurationInitializer;
import org.mumue.mumue.connection.AcceptorLoopRunnerBuilder;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.database.DatabaseAccessorInitializer;
import org.mumue.mumue.database.DatabaseInitializer;
import org.mumue.mumue.database.QueryRunnerInitializer;
import org.mumue.mumue.threading.InfiniteLoopRunner;
import org.mumue.mumue.threading.ThreadFactory;

import javax.inject.Inject;

public class Mumue {
    private final ConfigurationInitializer configurationInitializer;
    private QueryRunnerInitializer queryRunnerInitializer = new QueryRunnerInitializer();
    private DatabaseAccessorInitializer databaseAccessorInitializer = new DatabaseAccessorInitializer();
    private DatabaseInitializer databaseInitializer = new DatabaseInitializer();
    private AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder = new AcceptorLoopRunnerBuilder();
    private ThreadFactory threadFactory = new ThreadFactory();

    private ConnectionManager connectionManager = new ConnectionManager();

    @Inject
    public Mumue(ConfigurationInitializer configurationInitializer) {//, QueryRunnerInitializer queryRunnerInitializer, DatabaseAccessorInitializer databaseAccessorInitializer, DatabaseInitializer databaseInitializer, AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder, ThreadFactory threadFactory, ConnectionManager connectionManager) {
        this.configurationInitializer = configurationInitializer;
    }

    public void run() {
        Configuration configuration = configurationInitializer.initialize();
        queryRunnerInitializer.initialize(configuration);
        databaseAccessorInitializer.initialize();
        databaseInitializer.initialize();

        InfiniteLoopRunner acceptorLoop = startAcceptorLoop(configuration);

        //noinspection StatementWithEmptyBody
        while (acceptorLoop.isRunning() && !configuration.isTest()) ;

        acceptorLoop.stop();
    }

    private InfiniteLoopRunner startAcceptorLoop(Configuration configuration) {
        InfiniteLoopRunner connectionAcceptorLoop = acceptorLoopRunnerBuilder.build(configuration, connectionManager);

        Thread thread = threadFactory.create(connectionAcceptorLoop, "Connection Acceptor Thread");
        thread.start();
        return connectionAcceptorLoop;
    }
}
