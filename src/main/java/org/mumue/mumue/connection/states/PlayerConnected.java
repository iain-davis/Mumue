package org.mumue.mumue.connection.states;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.player.PlayerRepository;

@Singleton
class PlayerConnected implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final CurrentTimestampProvider currentTimestampProvider;
    private final PlayerRepository playerRepository;

    @Inject
    public PlayerConnected(ConnectionStateProvider connectionStateProvider, CurrentTimestampProvider currentTimestampProvider, PlayerMenuPrompt playerMenuPrompt, EnterUniverse enterUniverse, PlayerRepository playerRepository) {
        this.connectionStateProvider = connectionStateProvider;
        this.currentTimestampProvider = currentTimestampProvider;
        this.playerRepository = playerRepository;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        connection.getPlayer().countUse();
        connection.getPlayer().setLastUsed(currentTimestampProvider.get());
        playerRepository.save(connection.getPlayer());
        if (connection.getPortConfiguration().isSupportsMenus()) {
            return connectionStateProvider.get(PlayerMenuPrompt.class);
        }
        return connectionStateProvider.get(EnterUniverse.class);
    }
}
