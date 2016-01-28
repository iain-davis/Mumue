package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerRepository;

@Singleton
class LoginIdHandler implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final PlayerRepository playerRepository;

    @Inject
    public LoginIdHandler(ConnectionStateProvider connectionStateProvider, PlayerRepository playerRepository) {
        this.connectionStateProvider = connectionStateProvider;
        this.playerRepository = playerRepository;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        Player player = playerRepository.get(getLoginId(connection));
        if (player.getId() == GlobalConstants.REFERENCE_UNKNOWN) {
            return connectionStateProvider.get(NewPlayerPrompt.class);
        }
        return connectionStateProvider.get(PasswordPrompt.class);
    }

    private String getLoginId(Connection connection) {
        return connection.getInputQueue().peek();
    }
}
