package org.mumue.mumue.connection.stages;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;

public class NoOperation implements ConnectionStage {
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        return this;
    }
}
