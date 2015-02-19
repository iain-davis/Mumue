package org.ruhlendavis.mumue.connection.stages;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;

public class WaitForPasswordStage implements ConnectionStage {
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().size() < 2) {
            return this;
        }
        return new PlayerAuthenticationStage();
    }
}
