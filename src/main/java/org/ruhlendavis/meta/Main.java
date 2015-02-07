package org.ruhlendavis.meta;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationInitializer;
import org.ruhlendavis.meta.connection.Acceptor;
import org.ruhlendavis.meta.connection.ConnectionManager;
import org.ruhlendavis.meta.database.DatabaseInitializer;
import org.ruhlendavis.meta.database.QueryRunnerInitializer;
import org.ruhlendavis.meta.runner.InfiniteLoopRunner;

public class Main {
    private ConfigurationInitializer configurationInitializer = new ConfigurationInitializer();
    private QueryRunnerInitializer queryRunnerInitializer = new QueryRunnerInitializer();
    private DatabaseInitializer databaseInitializer = new DatabaseInitializer();
    private ThreadFactory threadFactory = new ThreadFactory();

    public static void main(String... arguments) {
        Main main = new Main();
        main.run(arguments);
    }

    public void run(String... arguments) {
        Configuration configuration = configurationInitializer.initialize(arguments);
        queryRunnerInitializer.initialize(configuration);
        databaseInitializer.initialize();

        InfiniteLoopRunner connectionAcceptorLoop = startConnectionAcceptor(configuration);

        //noinspection StatementWithEmptyBody
        while(connectionAcceptorLoop.isRunning() && !configuration.isTest()) {}

        connectionAcceptorLoop.stop();
    }

    private InfiniteLoopRunner startConnectionAcceptor(Configuration configuration) {
        Acceptor acceptor = new Acceptor(configuration.getTelnetPort(), new ConnectionManager());
        InfiniteLoopRunner connectionAcceptorLoop = new InfiniteLoopRunner(configuration, acceptor);
        Thread thread = threadFactory.create(connectionAcceptorLoop);
        thread.start();
        return connectionAcceptorLoop;
    }

    private void stopListener(InfiniteLoopRunner infiniteLoopRunner, Thread thread) {
        infiniteLoopRunner.stop();
        try {
            thread.join();
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }
}
