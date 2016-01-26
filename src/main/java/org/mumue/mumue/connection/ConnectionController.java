package org.mumue.mumue.connection;

import javax.inject.Inject;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.connection.states.ConnectionStateService;
import org.mumue.mumue.connection.states.WelcomeDisplay;
import org.mumue.mumue.threading.InfiniteLoopBody;

public class ConnectionController implements InfiniteLoopBody {
    private final ApplicationConfiguration configuration;
    private Connection connection;
    private ConnectionState state;

    @Inject
    public ConnectionController(ApplicationConfiguration configuration, ConnectionStateService connectionStateService, WelcomeDisplay welcomeDisplay) {
        this.configuration = configuration;
        this.state = connectionStateService.get(WelcomeDisplay.class);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean prepare() {
        return true;
    }

    @Override
    public boolean execute() {
        state = state.execute(connection, configuration);
        return true;
    }

    @Override
    public boolean cleanup() {
        return true;
    }

    public ConnectionState getState() {
        return state;
    }

    public ConnectionController withConnection(Connection connection) {
        this.connection = connection;
        return this;
    }
}
