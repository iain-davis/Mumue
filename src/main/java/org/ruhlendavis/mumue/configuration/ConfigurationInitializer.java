package org.ruhlendavis.mumue.configuration;

import javax.inject.Inject;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfigurationProvider;

public class ConfigurationInitializer {
    private final StartupConfigurationProvider startupConfigurationProvider;
    private final ConfigurationProvider configurationProvider;
    private final CommandLineConfiguration commandLineConfiguration;

    @Inject
    public ConfigurationInitializer(CommandLineConfiguration commandLineConfiguration, StartupConfigurationProvider startupConfigurationProvider, ConfigurationProvider configurationProvider) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.startupConfigurationProvider = startupConfigurationProvider;
        this.configurationProvider = configurationProvider;
    }

    public Configuration initialize() {
        StartupConfiguration startupConfiguration = startupConfigurationProvider.get();
        return configurationProvider.create(commandLineConfiguration, startupConfiguration, new OnlineConfiguration());
    }
}
