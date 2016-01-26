package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.player.PlayerRepository;

@Singleton
public class PlayerConnected implements ConnectionState {
    private final ConnectionStateService connectionStateService;
    private final CurrentTimestampProvider currentTimestampProvider;
    private final PlayerRepository playerRepository;

    @Inject
    public PlayerConnected(ConnectionStateService connectionStateService, CurrentTimestampProvider currentTimestampProvider, PlayerMenuPrompt playerMenuPrompt, EnterUniverse enterUniverse, PlayerRepository playerRepository) {
        this.connectionStateService = connectionStateService;
        this.currentTimestampProvider = currentTimestampProvider;
        this.playerRepository = playerRepository;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        connection.getPlayer().countUse();
        connection.getPlayer().setLastUsed(currentTimestampProvider.get());
        playerRepository.save(connection.getPlayer());
        if (connection.getPortConfiguration().isSupportsMenus()) {
            return connectionStateService.get(PlayerMenuPrompt.class);
        }
        return connectionStateService.get(EnterUniverse.class);
    }
}
