package org.ruhlendavis.meta.configuration;

import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.online.OnlineConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;

public class ConfigurationProvider {
    private static Configuration configuration;

    public Configuration create(CommandLineConfiguration commandLineConfiguration,
                                StartupConfiguration startupConfiguration,
                                OnlineConfiguration onlineConfiguration) {
        if (configuration == null) {
            configuration = new Configuration(commandLineConfiguration, startupConfiguration, onlineConfiguration);
        }
        return configuration;
    }

    public static Configuration get() {
        return configuration;
    }

    public void destroy() {
        configuration = null;
    }
}
