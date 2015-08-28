package org.mumue.mumue;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.configuration.ConfigurationInitializer;
import org.mumue.mumue.connection.AcceptorLoopRunnerBuilder;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.database.DatabaseAccessorInitializer;
import org.mumue.mumue.database.DatabaseAccessorProvider;
import org.mumue.mumue.database.DatabaseInitializer;
import org.mumue.mumue.threading.InfiniteLoopRunner;
import org.mumue.mumue.threading.ThreadFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;

public class Mumue {
    private final DataSource dataSource;
    private final ConfigurationInitializer configurationInitializer;
    private final DatabaseAccessorProvider databaseAccessorProvider;
    private final DatabaseInitializer databaseInitializer;
    private final AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder;
    private final ThreadFactory threadFactory;

    private final ConnectionManager connectionManager;
    private InfiniteLoopRunner acceptorLoop;

    @Inject
    public Mumue(ConfigurationInitializer configurationInitializer, DatabaseAccessorInitializer databaseAccessorInitializer, DataSource dataSource, DatabaseAccessorProvider databaseAccessorProvider, DatabaseInitializer databaseInitializer, AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder, ThreadFactory threadFactory, ConnectionManager connectionManager) {
        this.configurationInitializer = configurationInitializer;
        this.dataSource = dataSource;
        this.databaseAccessorProvider = databaseAccessorProvider;
        this.databaseInitializer = databaseInitializer;
        this.acceptorLoopRunnerBuilder = acceptorLoopRunnerBuilder;
        this.threadFactory = threadFactory;
        this.connectionManager = connectionManager;
    }

    public void run() {
        Configuration configuration = configurationInitializer.initialize();
        databaseInitializer.initialize();

        acceptorLoop = startAcceptorLoop(configuration);

        //noinspection StatementWithEmptyBody
        while (acceptorLoop.isRunning()) ;

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
        closeDatabase();
    }

    private void closeDatabase() {
        try {
            ((BasicDataSource)dataSource).close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
