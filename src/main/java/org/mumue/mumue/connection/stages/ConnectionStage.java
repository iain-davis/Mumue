package org.mumue.mumue.connection.stages;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

public interface ConnectionStage {
    ConnectionStage execute(Connection connection, ApplicationConfiguration configuration);
}
