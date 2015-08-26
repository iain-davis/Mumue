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
    private final QueryRunnerInitializer queryRunnerInitializer;
    private final DatabaseAccessorInitializer databaseAccessorInitializer;
    private final DatabaseInitializer databaseInitializer;
    private final AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder;
    private final ThreadFactory threadFactory;

    private final ConnectionManager connectionManager = new ConnectionManager();
    private InfiniteLoopRunner acceptorLoop;

    @Inject
    public Mumue(ConfigurationInitializer configurationInitializer, QueryRunnerInitializer queryRunnerInitializer, DatabaseAccessorInitializer databaseAccessorInitializer, DatabaseInitializer databaseInitializer, AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder, ThreadFactory threadFactory) {
        this.configurationInitializer = configurationInitializer;
        this.queryRunnerInitializer = queryRunnerInitializer;
        this.databaseAccessorInitializer = databaseAccessorInitializer;
        this.databaseInitializer = databaseInitializer;
        this.acceptorLoopRunnerBuilder = acceptorLoopRunnerBuilder;
        this.threadFactory = threadFactory;
    }

    public void run() {
        Configuration configuration = configurationInitializer.initialize();
        System.out.println("Database Url: " + configuration.getDatabaseUrl());
        queryRunnerInitializer.initialize(configuration);
        databaseAccessorInitializer.initialize();
        databaseInitializer.initialize();

        acceptorLoop = startAcceptorLoop(configuration);

        //noinspection StatementWithEmptyBody
        while (acceptorLoop.isRunning() && !configuration.isTest()) ;

        stop();
    }

    private InfiniteLoopRunner startAcceptorLoop(Configuration configuration) {
        InfiniteLoopRunner connectionAcceptorLoop = acceptorLoopRunnerBuilder.build(configuration, connectionManager);

        Thread thread = threadFactory.create(connectionAcceptorLoop, "Connection Acceptor Thread");
        thread.start();
        return connectionAcceptorLoop;
    }

    public void stop() {
        acceptorLoop.stop();
    }
}
