package org.ruhlendavis.meta;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.ruhlendavis.meta.configuration.ConfigurationModule;
import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineConfigurationFactory;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.database.DatabaseModule;
import org.ruhlendavis.meta.listener.Listener;

public class Main {
    private CommandLineConfigurationFactory commandLineConfigurationFactory = new CommandLineConfigurationFactory();
    private StartupConfigurationFactory startupConfigurationFactory = new StartupConfigurationFactory();

    public static void main(String... arguments) {
        Main main = new Main();
        main.run(arguments);
    }

    public void run(String... arguments) {
        CommandLineConfiguration commandLineConfiguration = commandLineConfigurationFactory.create(arguments);
        StartupConfiguration startupConfiguration = startupConfigurationFactory.create(commandLineConfiguration.getStartupConfigurationPath());

        Injector injector = Guice.createInjector(new ConfigurationModule(startupConfiguration), new DatabaseModule());

        Meta meta = injector.getInstance(Meta.class);
        meta.run(new Listener(), arguments);
    }
}
