package org.mumue.mumue.connection.stages.login;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.player.PlayerDao;

public class WaitForLoginId implements ConnectionStage {
    private final Injector injector;
    private final PlayerDao playerDao;

    @Inject
    public WaitForLoginId(Injector injector, PlayerDao playerDao) {
        this.injector = injector;
        this.playerDao = playerDao;
    }

    @Override
    public ConnectionStage execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        } else if (playerDao.playerExistsFor(getLoginId(connection))) {
            return injector.getInstance(PasswordPrompt.class);
        }
        return injector.getInstance(NewPlayerPrompt.class);
    }

    private String getLoginId(Connection connection) {
        return connection.getInputQueue().peek();
    }
}
