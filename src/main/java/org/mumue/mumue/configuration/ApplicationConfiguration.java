package org.mumue.mumue.configuration;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.mumue.mumue.configuration.online.OnlineConfiguration;

@Singleton
public class ApplicationConfiguration {
    private final OnlineConfiguration onlineConfiguration;
    private final ComponentIdManager componentIdManager;

    @Inject
    public ApplicationConfiguration(OnlineConfiguration onlineConfiguration, ComponentIdManager componentIdManager) {
        this.onlineConfiguration = onlineConfiguration;
        this.componentIdManager = componentIdManager;
    }

    public String getServerLocale() {
        return onlineConfiguration.getServerLocale();
    }

    public long getNewComponentId() {
        return componentIdManager.getNewComponentId();
    }
}
