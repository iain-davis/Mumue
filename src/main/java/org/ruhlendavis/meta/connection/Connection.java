package org.ruhlendavis.meta.connection;

import java.net.Socket;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.stages.ConnectionStage;
import org.ruhlendavis.meta.connection.stages.WelcomeStage;
import org.ruhlendavis.meta.runner.InfiniteLoopRunnerStarter;

public class Connection {
    private final TextQueue inputQueue = new TextQueue();
    private final TextQueue outputQueue = new TextQueue();
    private InfiniteLoopRunnerStarter infiniteLoopRunnerStarter = new InfiniteLoopRunnerStarter();

    public void initialize(Socket socket, Configuration configuration) {
        InputReceiver inputReceiver = new InputReceiver(socket, inputQueue);
        infiniteLoopRunnerStarter.start(inputReceiver);

        ConnectionController connectionController = new ConnectionController(inputQueue, outputQueue, configuration);
        infiniteLoopRunnerStarter.start(connectionController);

        OutputSender outputSender = new OutputSender(socket, outputQueue);
        infiniteLoopRunnerStarter.start(outputSender);
    }
}
