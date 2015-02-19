package org.ruhlendavis.mumue.configuration;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfigurationFactory;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfigurationFactory;

public class ConfigurationInitializer {
    private CommandLineConfigurationFactory commandLineConfigurationFactory = new CommandLineConfigurationFactory();
    private StartupConfigurationFactory startupConfigurationFactory = new StartupConfigurationFactory();
    private ConfigurationProvider configurationProvider = new ConfigurationProvider();

    public Configuration initialize(String... arguments) {
        CommandLineConfiguration commandLineConfiguration = commandLineConfigurationFactory.create(arguments);
        StartupConfiguration startupConfiguration = startupConfigurationFactory.create(commandLineConfiguration.getStartupConfigurationPath());
        return configurationProvider.create(commandLineConfiguration, startupConfiguration, new OnlineConfiguration());
    }
}
