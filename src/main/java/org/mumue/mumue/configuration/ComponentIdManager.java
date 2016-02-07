package org.mumue.mumue.configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.online.OnlineConfiguration;

@Singleton
public class ComponentIdManager {
    private static Long lastComponentId;
    private final OnlineConfiguration configuration;

    @Inject
    public ComponentIdManager(OnlineConfiguration configuration) {
        this.configuration = configuration;
    }

    synchronized public long getNewComponentId() {
        if (lastComponentId == null) {
            lastComponentId = configuration.getLastComponentId();
        }
        lastComponentId++;
        return lastComponentId;
    }
}
