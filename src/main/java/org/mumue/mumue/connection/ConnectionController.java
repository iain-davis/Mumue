package org.mumue.mumue.connection;

import com.google.inject.Injector;
import org.mumue.mumue.threading.InfiniteLoopBody;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.connection.stages.login.Welcome;

import javax.inject.Inject;

public class ConnectionController implements InfiniteLoopBody {
    private final Configuration configuration;
    private Connection connection;
    private ConnectionStage stage;

    @Inject
    public ConnectionController(Injector injector, Configuration configuration) {
        this(configuration, injector.getInstance(Welcome.class));
    }

    public ConnectionController(Configuration configuration, ConnectionStage stage) {
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

    public ConnectionStage getStage() {
        return stage;
    }

    public ConnectionController withConnection(Connection connection) {
        this.connection = connection;
        return this;
    }
}
