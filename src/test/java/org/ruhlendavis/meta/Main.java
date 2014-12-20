package org.ruhlendavis.meta;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.configuration.online.OnlineConfiguration;
import org.ruhlendavis.meta.configuration.online.OnlineConfigurationDao;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationNotFound;
import org.ruhlendavis.meta.database.DataSourceFactory;
import org.ruhlendavis.meta.database.QueryRunnerFactory;
import org.ruhlendavis.meta.listener.Listener;

public class Main {
    private StartupConfigurationFactory startupConfigurationFactory = new StartupConfigurationFactory();

    public static void main(String... arguments) {
        Main main = new Main();
        main.run(new Listener(), new CommandLineProvider(arguments));
    }

    public void run(Listener listener, CommandLineProvider commandLineProvider) {
        Configuration configuration = getConfiguration(commandLineProvider);

        Thread thread = startListener(listener, configuration.getTelnetPort());

        //noinspection StatementWithEmptyBody
        while(listener.isRunning() && !configuration.isTest()) {}

        stopListener(listener, thread);
    }

    private Configuration getConfiguration(CommandLineProvider commandLineProvider) {
        CommandLineConfiguration commandLineConfiguration = new CommandLineConfiguration(commandLineProvider.get());
        StartupConfiguration startupConfiguration = startupConfigurationFactory.create(commandLineConfiguration.getStartupConfigurationPath());
        try {
            startupConfiguration.load(commandLineConfiguration.getStartupConfigurationPath());
        } catch (StartupConfigurationNotFound exception) {

        }
        OnlineConfiguration onlineConfiguration = getOnlineConfiguration(startupConfiguration);

        return new Configuration(commandLineConfiguration, onlineConfiguration, startupConfiguration);
    }

    private OnlineConfiguration getOnlineConfiguration(StartupConfiguration startupConfiguration) {
        DataSource dataSource = new DataSourceFactory().createDataSource(startupConfiguration);
        QueryRunner queryRunner = new QueryRunnerFactory().createQueryRunner(dataSource);
        OnlineConfigurationDao dao = new OnlineConfigurationDao(queryRunner);
        return new OnlineConfiguration(dao);
    }

    private Thread startListener(Listener listener, int port) {
        Thread thread = new Thread(listener);
        listener.setPort(port);
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
