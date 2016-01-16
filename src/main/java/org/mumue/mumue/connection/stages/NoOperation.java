package org.mumue.mumue.connection.stages;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

public class NoOperation implements ConnectionStage {
    @Override
    public ConnectionStage execute(Connection connection, ApplicationConfiguration configuration) {
        return this;
    }
}
