package org.mumue.mumue.configuration;

import org.mumue.mumue.configuration.online.OnlineConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Configuration {
    private final OnlineConfiguration onlineConfiguration;
    private final ComponentIdManager componentIdManager;

    @Inject
    public Configuration(OnlineConfiguration onlineConfiguration) {
        this(onlineConfiguration, new ComponentIdManager());
    }

    public Configuration(OnlineConfiguration onlineConfiguration, ComponentIdManager componentIdManager) {
        this.onlineConfiguration = onlineConfiguration;
        this.componentIdManager = componentIdManager;
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
