package org.ruhlendavis.meta;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineConfigurationFactory;
import org.ruhlendavis.meta.configuration.online.OnlineConfiguration;
import org.ruhlendavis.meta.configuration.online.OnlineConfigurationDao;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.database.DataSourceProvider;
import org.ruhlendavis.meta.database.DatabaseInitializer;
import org.ruhlendavis.meta.database.DatabaseInitializerDao;
import org.ruhlendavis.meta.database.QueryRunnerProvider;
import org.ruhlendavis.meta.listener.Listener;
import org.ruhlendavis.meta.text.TextDao;

public class Meta {
    private CommandLineConfigurationFactory commandLineConfigurationFactory = new CommandLineConfigurationFactory();
    private StartupConfigurationFactory startupConfigurationFactory = new StartupConfigurationFactory();

    public void run(Listener listener, String... arguments) {
        Configuration configuration = getConfiguration(arguments);
        Thread thread = startListener(listener, configuration);

        //noinspection StatementWithEmptyBody
        while(listener.isRunning() && !configuration.isTest()) {}

        stopListener(listener, thread);
    }

    private Configuration getConfiguration(String... arguments) {
        CommandLineConfiguration commandLineConfiguration = commandLineConfigurationFactory.create(arguments);
        StartupConfiguration startupConfiguration = startupConfigurationFactory.create(commandLineConfiguration.getStartupConfigurationPath());
        OnlineConfiguration onlineConfiguration = getOnlineConfiguration(startupConfiguration);
        TextDao textDao = new TextDao(new QueryRunnerProvider(new DataSourceProvider(startupConfiguration).get()).get());
        return new Configuration(commandLineConfiguration, onlineConfiguration, startupConfiguration, textDao);
    }

    private OnlineConfiguration getOnlineConfiguration(StartupConfiguration startupConfiguration) {
        DataSource dataSource = new DataSourceProvider(startupConfiguration).get();
        QueryRunner queryRunner = new QueryRunnerProvider(dataSource).get();
        new DatabaseInitializer(new DatabaseInitializerDao(queryRunner)).initialize();
        OnlineConfigurationDao dao = new OnlineConfigurationDao(queryRunner);
        return new OnlineConfiguration(dao);
    }

    private Thread startListener(Listener listener, Configuration configuration) {
        Thread thread = new Thread(listener);
        listener.setPort(configuration.getTelnetPort());
        listener.setConfiguration(configuration);
        thread.start();
        return thread;
    }

    private void stopListener(Listener listener, Thread thread) {
        listener.stop();
        try {
            thread.join();
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }
}
