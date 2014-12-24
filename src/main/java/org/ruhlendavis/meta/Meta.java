package org.ruhlendavis.meta;

import java.io.PrintStream;
import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.ConfigurationDefaults;
import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineFactory;
import org.ruhlendavis.meta.configuration.online.OnlineConfiguration;
import org.ruhlendavis.meta.configuration.online.OnlineConfigurationDao;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationNotFound;
import org.ruhlendavis.meta.database.DataSourceFactory;
import org.ruhlendavis.meta.database.DatabaseInitializer;
import org.ruhlendavis.meta.database.DatabaseInitializerDao;
import org.ruhlendavis.meta.database.QueryRunnerFactory;
import org.ruhlendavis.meta.listener.Listener;
import org.ruhlendavis.meta.text.TextDao;

public class Meta {
    private StartupConfigurationFactory startupConfigurationFactory = new StartupConfigurationFactory();

    public static void main(String... arguments) {
        Meta meta = new Meta();
        meta.run(System.out, new Listener(), new CommandLineFactory(), "--test");
    }

    public void run(PrintStream output, Listener listener, CommandLineFactory commandLineFactory, String... arguments) {
        Configuration configuration = getConfiguration(output, commandLineFactory, arguments);
        Thread thread = startListener(listener, configuration);

        //noinspection StatementWithEmptyBody
        while(listener.isRunning() && !configuration.isTest()) {}

        stopListener(listener, thread);
    }

    private Configuration getConfiguration(PrintStream output, CommandLineFactory commandLineFactory, String... arguments) {
        CommandLineConfiguration commandLineConfiguration = new CommandLineConfiguration(commandLineFactory.create(arguments));
        StartupConfiguration startupConfiguration = startupConfigurationFactory.create(commandLineConfiguration.getStartupConfigurationPath());
        try {
            startupConfiguration.load(commandLineConfiguration.getStartupConfigurationPath());
        } catch (StartupConfigurationNotFound exception) {
            output.println("CRITICAL: Configuration file '" + ConfigurationDefaults.CONFIGURATION_PATH + "' not found.");
        }
        OnlineConfiguration onlineConfiguration = getOnlineConfiguration(startupConfiguration);
        TextDao textDao = new TextDao(new QueryRunnerFactory().create(new DataSourceFactory().create(startupConfiguration)));
        return new Configuration(commandLineConfiguration, onlineConfiguration, startupConfiguration, textDao);
    }

    private OnlineConfiguration getOnlineConfiguration(StartupConfiguration startupConfiguration) {
        DataSource dataSource = new DataSourceFactory().create(startupConfiguration);
        QueryRunner queryRunner = new QueryRunnerFactory().create(dataSource);
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
