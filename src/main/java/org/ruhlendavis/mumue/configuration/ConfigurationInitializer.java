package org.ruhlendavis.mumue.configuration;

import javax.inject.Inject;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfigurationFactory;

public class ConfigurationInitializer {
    private final StartupConfigurationFactory startupConfigurationFactory;
    private final ConfigurationProvider configurationProvider;
    private final CommandLineConfiguration commandLineConfiguration;

    @Inject
    public ConfigurationInitializer(CommandLineConfiguration commandLineConfiguration, StartupConfigurationFactory startupConfigurationFactory, ConfigurationProvider configurationProvider) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.startupConfigurationFactory = startupConfigurationFactory;
        this.configurationProvider = configurationProvider;
    }

    public Configuration initialize() {
        StartupConfiguration startupConfiguration = startupConfigurationFactory.create(commandLineConfiguration.getStartupConfigurationPath());
        return configurationProvider.create(commandLineConfiguration, startupConfiguration, new OnlineConfiguration());
    }
}
