package org.ruhlendavis.mumue.connection;

import java.net.Socket;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.runner.InfiniteLoopRunnerStarter;

public class ConnectionInitializer {
    private final TextQueue inputQueue = new TextQueue();
    private final TextQueue outputQueue = new TextQueue();
    private InfiniteLoopRunnerStarter infiniteLoopRunnerStarter = new InfiniteLoopRunnerStarter();

    public void initialize(Socket socket, Connection connection, Configuration configuration) {
        InputReceiver inputReceiver = new InputReceiver(socket, inputQueue);
        infiniteLoopRunnerStarter.start(inputReceiver);

        ConnectionController controller = new ConnectionController(connection, configuration);
        infiniteLoopRunnerStarter.start(controller);

        OutputSender outputSender = new OutputSender(socket, outputQueue);
        infiniteLoopRunnerStarter.start(outputSender);
    }
}