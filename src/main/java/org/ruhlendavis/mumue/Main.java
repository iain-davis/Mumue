package org.ruhlendavis.mumue;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.configuration.ConfigurationInitializer;
import org.ruhlendavis.mumue.connection.AcceptorLoopRunnerBuilder;
import org.ruhlendavis.mumue.connection.ConnectionManager;
import org.ruhlendavis.mumue.database.DatabaseAccessorInitializer;
import org.ruhlendavis.mumue.database.DatabaseInitializer;
import org.ruhlendavis.mumue.database.QueryRunnerInitializer;
import org.ruhlendavis.mumue.threading.InfiniteLoopRunner;
import org.ruhlendavis.mumue.threading.ThreadFactory;

public class Main {
    private ConfigurationInitializer configurationInitializer = new ConfigurationInitializer();
    private QueryRunnerInitializer queryRunnerInitializer = new QueryRunnerInitializer();
    private DatabaseAccessorInitializer databaseAccessorInitializer = new DatabaseAccessorInitializer();
    private DatabaseInitializer databaseInitializer = new DatabaseInitializer();
    private AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder = new AcceptorLoopRunnerBuilder();
    private ThreadFactory threadFactory = new ThreadFactory();

    private ConnectionManager connectionManager = new ConnectionManager();

    public static void main(String... arguments) {
        Main main = new Main();
        main.run(arguments);
    }

    public void run(String... arguments) {
        Configuration configuration = configurationInitializer.initialize(arguments);
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
