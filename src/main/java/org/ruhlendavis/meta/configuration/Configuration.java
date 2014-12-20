package org.ruhlendavis.meta.configuration;

import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.online.OnlineConfiguration;

public class Configuration {
    private CommandLineConfiguration commandLineConfiguration;
    private OnlineConfiguration onlineConfiguration;

    public Configuration(CommandLineConfiguration commandLineConfiguration, OnlineConfiguration onlineConfiguration) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.onlineConfiguration = onlineConfiguration;
    }

    public boolean isTest() {
        return commandLineConfiguration.isTest();
    }

    public String getServerLocale() {
        return onlineConfiguration.getServerLocale();
    }
}
