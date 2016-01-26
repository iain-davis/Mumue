package org.mumue.mumue.connection.states;

import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

@Singleton
public class NoOperation implements ConnectionState {
    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        return this;
    }
}
