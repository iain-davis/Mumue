package org.mumue.mumue.connection.stages.login;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;

public class WaitForPassword implements ConnectionStage {
    private final Injector injector;

    @Inject
    public WaitForPassword(Injector injector) {
        this.injector = injector;
    }

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().size() < 2) {
            return this;
        }
        return injector.getInstance(PlayerAuthentication.class);
    }
}
