package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerRepository;

public class LoginIdPromptHandler implements ConnectionState {
    private final StateCollection stateCollection;
    private final PlayerRepository playerRepository;

    @Inject
    @Singleton
    public LoginIdPromptHandler(StateCollection stateCollection, PlayerRepository playerRepository) {
        this.stateCollection = stateCollection;
        this.playerRepository = playerRepository;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        Player player = playerRepository.get(getLoginId(connection));
        if (player.getId() == GlobalConstants.REFERENCE_UNKNOWN) {
            return stateCollection.get(StateName.NewPlayerPrompt);
        }
        return stateCollection.get(StateName.PasswordPrompt);
    }

    private String getLoginId(Connection connection) {
        return connection.getInputQueue().peek();
    }
}
