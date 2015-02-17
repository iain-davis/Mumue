package org.ruhlendavis.meta.connection;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.stages.ConnectionStage;
import org.ruhlendavis.meta.connection.stages.WelcomeStage;
import org.ruhlendavis.meta.runner.InfiniteLoopBody;

public class ConnectionController implements InfiniteLoopBody {
    private final TextQueue inputQueue;
    private final TextQueue outputQueue;
    private final Configuration configuration;
    private ConnectionStage stage;

    public ConnectionController(TextQueue inputQueue, TextQueue outputQueue, Configuration configuration) {
        this(inputQueue, outputQueue, configuration, new WelcomeStage());
    }

    public ConnectionController(TextQueue inputQueue, TextQueue outputQueue, Configuration configuration, ConnectionStage stage) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.configuration = configuration;
        this.stage = stage;
    }

    @Override
    public void prepare() {

    }

    @Override
    public void execute() {
        stage = stage.execute(inputQueue, outputQueue, configuration);
    }

    @Override
    public void cleanup() {

    }

    public ConnectionStage getStage() {
        return stage;
    }
}
