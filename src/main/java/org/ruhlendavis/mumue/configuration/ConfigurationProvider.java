package org.ruhlendavis.mumue.configuration;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;

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
