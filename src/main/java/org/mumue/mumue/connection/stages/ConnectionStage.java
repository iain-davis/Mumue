package org.mumue.mumue.connection.stages;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;

public interface ConnectionStage {
    public ConnectionStage execute(Connection connection, Configuration configuration);
}
