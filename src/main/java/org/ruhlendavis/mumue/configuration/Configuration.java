package org.ruhlendavis.mumue.configuration;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;
import org.ruhlendavis.mumue.configuration.startup.StartupConfiguration;

public class Configuration {
    private CommandLineConfiguration commandLineConfiguration;
    private OnlineConfiguration onlineConfiguration;
    private StartupConfiguration startupConfiguration;

    public Configuration(CommandLineConfiguration commandLineConfiguration, StartupConfiguration startupConfiguration, OnlineConfiguration onlineConfiguration) {
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

    public String getDatabaseUsername() {
        return startupConfiguration.getDatabaseUsername();
    }

    public String getDatabasePassword() {
        return startupConfiguration.getDatabasePassword();
    }

    public String getDatabasePath() {
        return startupConfiguration.getDatabasePath();
    }

    public long getNextComponentId() {
        return onlineConfiguration.getNextComponentId();
    }
}
