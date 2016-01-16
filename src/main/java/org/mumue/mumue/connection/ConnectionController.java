package org.mumue.mumue.connection;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.connection.stages.login.Welcome;
import org.mumue.mumue.threading.InfiniteLoopBody;

public class ConnectionController implements InfiniteLoopBody {
    private final ApplicationConfiguration configuration;
    private Connection connection;
    private ConnectionStage stage;

    @Inject
    public ConnectionController(Injector injector, ApplicationConfiguration configuration) {
        this(configuration, injector.getInstance(Welcome.class));
    }

    public ConnectionController(ApplicationConfiguration configuration, ConnectionStage stage) {
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
