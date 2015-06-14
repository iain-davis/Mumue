package org.mumue.mumue.configuration;

import org.mumue.mumue.configuration.online.OnlineConfiguration;

class ComponentIdManager {
    private static Long lastComponentId;

    synchronized public long getNewComponentId(OnlineConfiguration configuration) {
        if (lastComponentId == null) {
            lastComponentId = configuration.getLastComponentId();
        }
        lastComponentId++;
        return lastComponentId;
    }
}
