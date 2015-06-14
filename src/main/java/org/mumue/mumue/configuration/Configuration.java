package org.mumue.mumue.configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.online.OnlineConfiguration;
import org.mumue.mumue.configuration.startup.StartupConfiguration;
import org.mumue.mumue.configuration.commandline.CommandLineConfiguration;

@Singleton
public class Configuration {
    private final CommandLineConfiguration commandLineConfiguration;
    private final OnlineConfiguration onlineConfiguration;
    private final StartupConfiguration startupConfiguration;
    private final ComponentIdManager componentIdManager;

    @Inject
    public Configuration(CommandLineConfiguration commandLineConfiguration, StartupConfiguration startupConfiguration, OnlineConfiguration onlineConfiguration) {
        this(commandLineConfiguration, startupConfiguration, onlineConfiguration, new ComponentIdManager());
    }

    public Configuration(CommandLineConfiguration commandLineConfiguration, StartupConfiguration startupConfiguration, OnlineConfiguration onlineConfiguration, ComponentIdManager componentIdManager) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.onlineConfiguration = onlineConfiguration;
        this.startupConfiguration = startupConfiguration;
        this.componentIdManager = componentIdManager;
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

    public long getNewComponentId() {
        return componentIdManager.getNewComponentId(onlineConfiguration);
    }
}
