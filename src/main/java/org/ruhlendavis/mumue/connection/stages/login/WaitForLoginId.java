package org.ruhlendavis.mumue.connection.stages.login;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.PlayerDao;

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
