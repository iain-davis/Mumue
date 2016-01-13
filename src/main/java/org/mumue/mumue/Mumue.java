package org.mumue.mumue;

import com.google.inject.Injector;
import org.apache.commons.dbcp2.BasicDataSource;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.configuration.ConfigurationInitializer;
import org.mumue.mumue.connection.ConnectionFactory;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.connection.Acceptor;
import org.mumue.mumue.connection.ServerSocketFactory;
import org.mumue.mumue.database.DatabaseAccessorProvider;
import org.mumue.mumue.database.DatabaseInitializer;
import org.mumue.mumue.threading.InfiniteLoopRunner;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Mumue {
    private final DataSource dataSource;
    private final ConfigurationInitializer configurationInitializer;
    private final DatabaseInitializer databaseInitializer;
    private final ConnectionManager connectionManager;
    private final Injector injector;
    private Future<?> acceptorTask;

    @SuppressWarnings("UnusedParameters")
    @Inject
    public Mumue(DataSource dataSource, DatabaseInitializer databaseInitializer,
                 ConfigurationInitializer configurationInitializer,
                 ConnectionManager connectionManager,
                 Injector injector,
                 DatabaseAccessorProvider databaseAccessorProvider) {
        this.configurationInitializer = configurationInitializer;
        this.dataSource = dataSource;
        this.databaseInitializer = databaseInitializer;
        this.connectionManager = connectionManager;
        this.injector = injector;
    }

    public void run() {
        Configuration configuration = configurationInitializer.initialize();
        databaseInitializer.initialize();

        Acceptor acceptor = new Acceptor(
                injector.getInstance(ServerSocketFactory.class),
                injector.getInstance(ConnectionFactory.class),
                injector.getInstance(Configuration.class),
                connectionManager,
                configuration.getTelnetPort()
        );

        ExecutorService executorService = Executors.newCachedThreadPool();
        acceptorTask = executorService.submit(new InfiniteLoopRunner(acceptor));
        //noinspection StatementWithEmptyBody
        while(!acceptorTask.isDone()) {}

        cleanup();
    }

    public void stop() {
        acceptorTask.cancel(true);
    }

    private void cleanup() {
        try {
            ((BasicDataSource)dataSource).close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
