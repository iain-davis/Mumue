package org.mumue.mumue;

import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.Future;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mumue.mumue.configuration.PortConfigurationRepository;
import org.mumue.mumue.database.DatabaseInitializer;

public class Mumue {
    private final AcceptorsBuilder acceptorsBuilder;
    private final AcceptorStarter acceptorStarter;
    private final PortConfigurationRepository repository;
    private final DatabaseInitializer database;
    private final DataSource dataSource;
    private Collection<Future<?>> acceptorTasks;

    @Inject
    public Mumue(AcceptorsBuilder acceptorsBuilder, AcceptorStarter acceptorStarter, PortConfigurationRepository repository, DatabaseInitializer database, DataSource dataSource) {
        this.acceptorsBuilder = acceptorsBuilder;
        this.acceptorStarter = acceptorStarter;
        this.repository = repository;
        this.database = database;
        this.dataSource = dataSource;
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

        cleanup();
    }

    public void stop() {
        for (Future<?> task : acceptorTasks) {
            task.cancel(true);
        }
    }

    private void cleanup() {
        try {
            ((BasicDataSource) dataSource).close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
