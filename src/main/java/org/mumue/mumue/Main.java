package org.mumue.mumue;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.mumue.mumue.configuration.commandline.CommandLineConfigurationModule;
import org.mumue.mumue.connection.AcceptorLoopRunnerBuilder;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.configuration.ConfigurationInitializer;
import org.mumue.mumue.configuration.startup.StartupConfigurationModule;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.database.DatabaseAccessorInitializer;
import org.mumue.mumue.database.DatabaseInitializer;
import org.mumue.mumue.database.QueryRunnerInitializer;
import org.mumue.mumue.threading.InfiniteLoopRunner;
import org.mumue.mumue.threading.ThreadFactory;

public class Main {
    private final ConfigurationInitializer configurationInitializer;
    private QueryRunnerInitializer queryRunnerInitializer = new QueryRunnerInitializer();
    private DatabaseAccessorInitializer databaseAccessorInitializer = new DatabaseAccessorInitializer();
    private DatabaseInitializer databaseInitializer = new DatabaseInitializer();
    private AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder = new AcceptorLoopRunnerBuilder();
    private ThreadFactory threadFactory = new ThreadFactory();

    private ConnectionManager connectionManager = new ConnectionManager();

    public static void main(String... arguments) {
        Injector injector = Guice.createInjector(
                new CommandLineConfigurationModule(arguments),
                new StartupConfigurationModule()
        );
        Main main = injector.getInstance(Main.class);
        main.run();
    }

    @Inject
    public Main(ConfigurationInitializer configurationInitializer) {//, QueryRunnerInitializer queryRunnerInitializer, DatabaseAccessorInitializer databaseAccessorInitializer, DatabaseInitializer databaseInitializer, AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder, ThreadFactory threadFactory, ConnectionManager connectionManager) {
        this.configurationInitializer = configurationInitializer;
    }

    public void run() {
        Configuration configuration = configurationInitializer.initialize();
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
