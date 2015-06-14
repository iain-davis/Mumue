package org.mumue.mumue.connection.stages.login;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.player.PlayerDao;

public class WaitForLoginId implements ConnectionStage {
    private PlayerDao dao = new PlayerDao();
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        } else if (dao.playerExistsFor(getLoginId(connection))) {
            return new PasswordPrompt();
        }
        return new NewPlayerPrompt();
    }

    private String getLoginId(Connection connection) {
        return connection.getInputQueue().peek();
    }
}
