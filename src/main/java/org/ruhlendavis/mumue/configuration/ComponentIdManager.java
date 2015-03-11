package org.ruhlendavis.mumue.configuration;

import org.ruhlendavis.mumue.configuration.online.OnlineConfiguration;

public class ComponentIdManager {
    private static Long lastComponentId;

    synchronized public long getNewComponentId(OnlineConfiguration configuration) {
        if (lastComponentId == null) {
            lastComponentId = configuration.getLastComponentId();
        }
        lastComponentId++;
        return lastComponentId;
    }
}
