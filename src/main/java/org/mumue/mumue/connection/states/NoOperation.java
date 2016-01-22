package org.mumue.mumue.connection.states;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

public class NoOperation implements ConnectionState {
    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        return this;
    }
}
