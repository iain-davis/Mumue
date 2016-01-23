package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.PlayerDao;

public class LoginIdPromptHandler implements ConnectionState {
    private final StateCollection stateCollection;
    private final PlayerDao playerDao;

    @Inject
    public LoginIdPromptHandler(StateCollection stateCollection, PlayerDao playerDao) {
        this.stateCollection = stateCollection;
        this.playerDao = playerDao;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        } else if (playerDao.playerExistsFor(getLoginId(connection))) {
            return stateCollection.get(StateName.PasswordPrompt);
        }
        return stateCollection.get(StateName.NewPlayerPrompt);
    }

    private String getLoginId(Connection connection) {
        return connection.getInputQueue().peek();
    }
}
