package org.mumue.mumue;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Acceptor;
import org.mumue.mumue.database.DatabaseInitializer;
import org.mumue.mumue.threading.InfiniteLoopRunner;

public class Mumue {
    private final Acceptor acceptor;
    private final ApplicationConfiguration configuration;
    private final DatabaseInitializer database;
    private final DataSource dataSource;
    private final ExecutorService executorService;
    private Future<?> acceptorTask;

    @Inject
    public Mumue(Acceptor acceptor, ApplicationConfiguration configuration, DatabaseInitializer database, DataSource dataSource, ExecutorService executorService) {
        this.acceptor = acceptor;
        this.configuration = configuration;
        this.database = database;
        this.dataSource = dataSource;
        this.executorService = executorService;
    }

    public void run() {
        database.initialize();

        acceptor.setPort(configuration.getTelnetPort());
        acceptorTask = executorService.submit(new InfiniteLoopRunner(acceptor));
        //noinspection StatementWithEmptyBody
        while (!acceptorTask.isDone()) {
        }

        cleanup();
    }

    public void stop() {
        acceptorTask.cancel(true);
    }

    private void cleanup() {
        try {
            ((BasicDataSource) dataSource).close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
