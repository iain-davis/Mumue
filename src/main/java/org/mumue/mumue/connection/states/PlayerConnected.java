package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.connection.states.mainmenu.DisplayPlayerMenu;

public class PlayerConnected implements ConnectionState {
    private final CurrentTimestampProvider currentTimestampProvider;
    private final DisplayPlayerMenu displayPlayerMenu;
    private final EnterUniverse enterUniverse;

    @Inject
    @Singleton
    public PlayerConnected(CurrentTimestampProvider currentTimestampProvider, DisplayPlayerMenu displayPlayerMenu, EnterUniverse enterUniverse) {
        this.currentTimestampProvider = currentTimestampProvider;
        this.displayPlayerMenu = displayPlayerMenu;
        this.enterUniverse = enterUniverse;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        connection.getPlayer().countUse();
        connection.getPlayer().setLastModified(currentTimestampProvider.get());
        if (connection.getPortConfiguration().isSupportsMenus()) {
            return displayPlayerMenu;
        }
        return enterUniverse;
    }
}
