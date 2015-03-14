package org.ruhlendavis.mumue.configuration;

import javax.inject.Inject;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;

public class ConfigurationInitializer {
    private StartupConfiguration startupConfiguration;
    private OnlineConfiguration onlineConfiguration;
    private final ConfigurationProvider configurationProvider;
    private final CommandLineConfiguration commandLineConfiguration;

    @Inject
    public ConfigurationInitializer(CommandLineConfiguration commandLineConfiguration, StartupConfiguration startupConfiguration, OnlineConfiguration onlineConfiguration, ConfigurationProvider configurationProvider) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.startupConfiguration = startupConfiguration;
        this.onlineConfiguration = onlineConfiguration;
        this.configurationProvider = configurationProvider;
    }

    public Configuration initialize() {
        return configurationProvider.create(commandLineConfiguration, startupConfiguration, onlineConfiguration);
    }
}
