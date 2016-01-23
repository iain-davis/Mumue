package org.mumue.mumue.connection;

import javax.inject.Inject;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.connection.states.StateCollection;
import org.mumue.mumue.connection.states.StateName;
import org.mumue.mumue.threading.InfiniteLoopBody;

public class ConnectionController implements InfiniteLoopBody {
    private final ApplicationConfiguration configuration;
    private Connection connection;
    private ConnectionState stage;

    @Inject
    public ConnectionController(ApplicationConfiguration configuration, StateCollection stateCollection) {
        this(configuration, stateCollection.get(StateName.WelcomeDisplay));
    }

    public ConnectionController(ApplicationConfiguration configuration, ConnectionState stage) {
        this.configuration = configuration;
        this.stage = stage;
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
        stage = stage.execute(connection, configuration);
        return true;
    }

    @Override
    public boolean cleanup() {
        return true;
    }

    public ConnectionState getStage() {
        return stage;
    }

    public ConnectionController withConnection(Connection connection) {
        this.connection = connection;
        return this;
    }
}
