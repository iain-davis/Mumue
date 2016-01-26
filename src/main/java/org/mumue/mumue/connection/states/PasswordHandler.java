package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

@Singleton
public class PasswordHandler implements ConnectionState {
    private final ConnectionStateService connectionStateService;

    @Inject
    public PasswordHandler(ConnectionStateService connectionStateService) {
        this.connectionStateService = connectionStateService;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().size() < 2) {
            return this;
        }
        return connectionStateService.get(PlayerAuthentication.class);
    }
}
