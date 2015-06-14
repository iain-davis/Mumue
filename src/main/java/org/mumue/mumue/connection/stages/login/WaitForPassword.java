package org.mumue.mumue.connection.stages.login;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;

public class WaitForPassword implements ConnectionStage {
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().size() < 2) {
            return this;
        }
        return new PlayerAuthentication();
    }
}
