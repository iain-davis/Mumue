package org.ruhlendavis.meta.configuration;

import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.online.OnlineConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.text.TextDao;

public class Configuration {
    private CommandLineConfiguration commandLineConfiguration;
    private OnlineConfiguration onlineConfiguration;
    private StartupConfiguration startupConfiguration;

    public Configuration(CommandLineConfiguration commandLineConfiguration, OnlineConfiguration onlineConfiguration, StartupConfiguration startupConfiguration) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.onlineConfiguration = onlineConfiguration;
        this.startupConfiguration = startupConfiguration;
    }

    public boolean isTest() {
        return commandLineConfiguration.isTest();
    }

    public String getServerLocale() {
        return onlineConfiguration.getServerLocale();
    }

    public int getTelnetPort() {
        return startupConfiguration.getTelnetPort();
    }
}
