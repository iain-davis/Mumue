package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.player.PlayerRepository;

public class PlayerConnected implements ConnectionState {
    private final CurrentTimestampProvider currentTimestampProvider;
    private final PlayerMenuDisplay playerMenuDisplay;
    private final EnterUniverse enterUniverse;
    private final PlayerRepository playerRepository;

    @Inject
    @Singleton
    public PlayerConnected(CurrentTimestampProvider currentTimestampProvider, PlayerMenuDisplay playerMenuDisplay, EnterUniverse enterUniverse, PlayerRepository playerRepository) {
        this.currentTimestampProvider = currentTimestampProvider;
        this.playerMenuDisplay = playerMenuDisplay;
        this.enterUniverse = enterUniverse;
        this.playerRepository = playerRepository;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        connection.getPlayer().countUse();
        connection.getPlayer().setLastUsed(currentTimestampProvider.get());
        playerRepository.save(connection.getPlayer());
        if (connection.getPortConfiguration().isSupportsMenus()) {
            return playerMenuDisplay;
        }
        return enterUniverse;
    }
}
