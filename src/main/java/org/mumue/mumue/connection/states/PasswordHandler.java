package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

@Singleton
class PasswordHandler implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;

    @Inject
    public PasswordHandler(ConnectionStateProvider connectionStateProvider) {
        this.connectionStateProvider = connectionStateProvider;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().size() < 2) {
            return this;
        }
        return connectionStateProvider.get(PlayerAuthentication.class);
    }
}
