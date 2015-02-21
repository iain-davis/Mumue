package org.ruhlendavis.mumue.connection;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.loginphase.WelcomeStage;
import org.ruhlendavis.mumue.runner.InfiniteLoopBody;

public class ConnectionController implements InfiniteLoopBody {
    private final Connection connection;
    private final Configuration configuration;
    private ConnectionStage stage;

    public ConnectionController(Connection connection, Configuration configuration) {
        this(connection, configuration, new WelcomeStage());
    }

    public ConnectionController(Connection connection, Configuration configuration, ConnectionStage stage) {
        this.connection = connection;
        this.configuration = configuration;
        this.stage = stage;
    }

    @Override
    public void prepare() {

    }

    @Override
    public void execute() {
        stage = stage.execute(connection, configuration);
    }

    @Override
    public void cleanup() {

    }

    public ConnectionStage getStage() {
        return stage;
    }
}
