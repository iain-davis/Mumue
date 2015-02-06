package org.ruhlendavis.meta;

import javax.sql.DataSource;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationProvider;
import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineConfigurationFactory;
import org.ruhlendavis.meta.configuration.online.OnlineConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.database.DataSourceFactory;
import org.ruhlendavis.meta.database.DatabaseInitializer;
import org.ruhlendavis.meta.database.QueryRunnerProvider;
import org.ruhlendavis.meta.listener.Listener;
import org.ruhlendavis.meta.runner.InfiniteLoopRunner;

public class Main {
    private CommandLineConfigurationFactory commandLineConfigurationFactory = new CommandLineConfigurationFactory();
    private StartupConfigurationFactory startupConfigurationFactory = new StartupConfigurationFactory();
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();
    private QueryRunnerProvider queryRunnerProvider = new QueryRunnerProvider();
    private ConfigurationProvider configurationProvider = new ConfigurationProvider();
    private DatabaseInitializer databaseInitializer = new DatabaseInitializer();

    public static void main(String... arguments) {
        Main main = new Main();
        main.run(arguments);
    }

    public void run(String... arguments) {
        initializeConfiguration(arguments);
        databaseInitializer.initialize();

        Configuration configuration = ConfigurationProvider.get();
        Listener listener = new Listener(configuration.getTelnetPort());
        InfiniteLoopRunner infiniteLoopRunner = new InfiniteLoopRunner(configuration, listener);
        Thread thread = new Thread(infiniteLoopRunner);
        thread.start();

        //noinspection StatementWithEmptyBody
        while(infiniteLoopRunner.isRunning() && !configuration.isTest()) {}

        stopListener(infiniteLoopRunner, thread);
    }

    private void initializeConfiguration(String... arguments) {
        CommandLineConfiguration commandLineConfiguration = commandLineConfigurationFactory.create(arguments);
        StartupConfiguration startupConfiguration = startupConfigurationFactory.create(commandLineConfiguration.getStartupConfigurationPath());
        DataSource dataSource = dataSourceFactory.create(startupConfiguration);
        queryRunnerProvider.create(dataSource);
        configurationProvider.create(commandLineConfiguration, startupConfiguration, new OnlineConfiguration());
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
