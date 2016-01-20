package org.mumue.mumue;

import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.PortConfigurationRepository;
import org.mumue.mumue.connection.Acceptor;
import org.mumue.mumue.database.DatabaseInitializer;
import org.mumue.mumue.threading.InfiniteLoopRunner;

public class Mumue {
    private final AcceptorsBuilder acceptorsBuilder;
    private final AcceptorStarter acceptorStarter;
    private final PortConfigurationRepository repository;
    private final Acceptor acceptor;
    private final ApplicationConfiguration configuration;
    private final DatabaseInitializer database;
    private final DataSource dataSource;
    private final ExecutorService executorService;
    private Future<?> acceptorTask;
    private Collection<Future<?>> acceptorTasks;

    @Inject
    public Mumue(AcceptorsBuilder acceptorsBuilder, AcceptorStarter acceptorStarter, PortConfigurationRepository repository, Acceptor acceptor, ApplicationConfiguration configuration, DatabaseInitializer database, DataSource dataSource, ExecutorService executorService) {
        this.acceptorsBuilder = acceptorsBuilder;
        this.acceptorStarter = acceptorStarter;
        this.repository = repository;
        this.acceptor = acceptor;
        this.configuration = configuration;
        this.database = database;
        this.dataSource = dataSource;
        this.executorService = executorService;
    }

    public void run() {
        database.initialize();

        acceptorTasks = acceptorStarter.start(acceptorsBuilder.build(repository.getAll()));

        boolean running = true;
        while (running) {
            boolean done = true;
            for (Future<?> task : acceptorTasks) {
                done = done & task.isDone();
            }
            running = !done;
        }
//
//        acceptorTask = executorService.submit(new InfiniteLoopRunner(acceptor));
//        //noinspection StatementWithEmptyBody
//        while (!acceptorTask.isDone()) {
//        }

        cleanup();
    }

    public void stop() {
        for (Future<?> task : acceptorTasks) {
            task.cancel(true);
        }
//        acceptorTask.cancel(true);
    }

    private void cleanup() {
        try {
            ((BasicDataSource) dataSource).close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
