package org.mumue.mumue.connection.states;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

public interface ConnectionState {
    ConnectionState execute(Connection connection, ApplicationConfiguration configuration);
}
