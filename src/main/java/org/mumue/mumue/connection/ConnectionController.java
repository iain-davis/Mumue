package org.mumue.mumue.connection;

import org.mumue.mumue.threading.InfiniteLoopBody;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.connection.stages.login.Welcome;

public class ConnectionController implements InfiniteLoopBody {
    private final Connection connection;
    private final Configuration configuration;
    private ConnectionStage stage;

    public ConnectionController(Connection connection, Configuration configuration) {
        this(connection, configuration, new Welcome());
    }

    public ConnectionController(Connection connection, Configuration configuration, ConnectionStage stage) {
        this.connection = connection;
        this.configuration = configuration;
        this.stage = stage;
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

    public ConnectionStage getStage() {
        return stage;
    }
}
