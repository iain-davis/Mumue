package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

public class PasswordPromptHandler implements ConnectionState {
    private final StateCollection stateCollection;

    @Inject
    public PasswordPromptHandler(StateCollection stateCollection) {
        this.stateCollection = stateCollection;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().size() < 2) {
            return this;
        }
        return stateCollection.get(StateName.PlayerAuthentication);
    }
}
