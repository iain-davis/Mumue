package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.player.PlayerRepository;

public class PlayerConnected implements ConnectionState {
    private final CurrentTimestampProvider currentTimestampProvider;
    private final DisplayPlayerMenu displayPlayerMenu;
    private final EnterUniverse enterUniverse;
    private final PlayerRepository playerRepository;

    @Inject
    @Singleton
    public PlayerConnected(CurrentTimestampProvider currentTimestampProvider, DisplayPlayerMenu displayPlayerMenu, EnterUniverse enterUniverse, PlayerRepository playerRepository) {
        this.currentTimestampProvider = currentTimestampProvider;
        this.displayPlayerMenu = displayPlayerMenu;
        this.enterUniverse = enterUniverse;
        this.playerRepository = playerRepository;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        connection.getPlayer().countUse();
        connection.getPlayer().setLastUsed(currentTimestampProvider.get());
        playerRepository.save(connection.getPlayer());
        if (connection.getPortConfiguration().isSupportsMenus()) {
            return displayPlayerMenu;
        }
        return enterUniverse;
    }
}
