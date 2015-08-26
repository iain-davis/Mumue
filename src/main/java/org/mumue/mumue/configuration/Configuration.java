package org.mumue.mumue.configuration;

import org.mumue.mumue.configuration.commandline.CommandLineConfiguration;
import org.mumue.mumue.configuration.online.OnlineConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Configuration {
    private final CommandLineConfiguration commandLineConfiguration;
    private final OnlineConfiguration onlineConfiguration;
    private final ComponentIdManager componentIdManager;

    @Inject
    public Configuration(CommandLineConfiguration commandLineConfiguration, OnlineConfiguration onlineConfiguration) {
        this(commandLineConfiguration, onlineConfiguration, new ComponentIdManager());
    }

    public Configuration(CommandLineConfiguration commandLineConfiguration, OnlineConfiguration onlineConfiguration, ComponentIdManager componentIdManager) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.onlineConfiguration = onlineConfiguration;
        this.componentIdManager = componentIdManager;
    }

    public boolean isTest() {
        return commandLineConfiguration.isTest();
    }

    public String getServerLocale() {
        return onlineConfiguration.getServerLocale();
    }

    public int getTelnetPort() {
        return onlineConfiguration.getTelnetPort();
    }

    public long getNewComponentId() {
        return componentIdManager.getNewComponentId(onlineConfiguration);
    }
}
