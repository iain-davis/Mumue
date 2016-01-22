package org.mumue.mumue.connection.states.login;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;

public class WaitForPassword implements ConnectionState {
    private final Injector injector;

    @Inject
    public WaitForPassword(Injector injector) {
        this.injector = injector;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().size() < 2) {
            return this;
        }
        return injector.getInstance(PlayerAuthentication.class);
    }
}
