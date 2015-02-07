package org.ruhlendavis.meta;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationInitializer;
import org.ruhlendavis.meta.database.DatabaseInitializer;
import org.ruhlendavis.meta.database.QueryRunnerInitializer;
import org.ruhlendavis.meta.listener.Listener;
import org.ruhlendavis.meta.runner.InfiniteLoopRunner;

public class Main {
    private ConfigurationInitializer configurationInitializer = new ConfigurationInitializer();
    private QueryRunnerInitializer queryRunnerInitializer = new QueryRunnerInitializer();
    private DatabaseInitializer databaseInitializer = new DatabaseInitializer();

    public static void main(String... arguments) {
        Main main = new Main();
        main.run(arguments);
    }

    public void run(String... arguments) {
        Configuration configuration = configurationInitializer.initialize(arguments);
        queryRunnerInitializer.initialize(configuration);
        databaseInitializer.initialize();

        Listener listener = new Listener(configuration.getTelnetPort(), new ConnectionManager());
        InfiniteLoopRunner infiniteLoopRunner = new InfiniteLoopRunner(configuration, listener);
        Thread thread = new Thread(infiniteLoopRunner);
        thread.start();

        //noinspection StatementWithEmptyBody
        while(infiniteLoopRunner.isRunning() && !configuration.isTest()) {}

        stopListener(infiniteLoopRunner, thread);
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
