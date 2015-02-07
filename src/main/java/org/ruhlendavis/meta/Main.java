package org.ruhlendavis.meta;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationInitializer;
import org.ruhlendavis.meta.connection.ConnectionAcceptor;
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

        InfiniteLoopRunner acceptorLoop = startAcceptorLoop(configuration);

        //noinspection StatementWithEmptyBody
        while(acceptorLoop.isRunning() && !configuration.isTest()) {}

        acceptorLoop.stop();
    }

    private InfiniteLoopRunner startAcceptorLoop(Configuration configuration) {
        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(configuration.getTelnetPort(), new ConnectionManager());
        InfiniteLoopRunner connectionAcceptorLoop = new InfiniteLoopRunner(configuration, connectionAcceptor);
        Thread thread = threadFactory.create(connectionAcceptorLoop);
        thread.start();
        return connectionAcceptorLoop;
    }
}
