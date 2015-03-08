package org.ruhlendavis.mumue.connection;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.login.Welcome;
import org.ruhlendavis.mumue.threading.InfiniteLoopBody;

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
