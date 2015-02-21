package org.ruhlendavis.mumue.connection;

import java.net.Socket;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.runner.InfiniteLoopRunnerStarter;
import org.ruhlendavis.mumue.texttransformer.LineSeparatorTransformer;
import org.ruhlendavis.mumue.texttransformer.TransformerEngine;

public class ConnectionInitializer {
    private InfiniteLoopRunnerStarter infiniteLoopRunnerStarter = new InfiniteLoopRunnerStarter();

    public void initialize(Socket socket, Connection connection, Configuration configuration) {
        InputReceiver inputReceiver = new InputReceiver(socket, connection.getInputQueue());
        infiniteLoopRunnerStarter.start(inputReceiver);

        ConnectionController controller = new ConnectionController(connection, configuration);
        infiniteLoopRunnerStarter.start(controller);

        TransformerEngine transformerEngine = new TransformerEngine();
        transformerEngine.add(new LineSeparatorTransformer());

        OutputSender outputSender = new OutputSender(socket, connection.getOutputQueue(), transformerEngine);
        infiniteLoopRunnerStarter.start(outputSender);
    }
}
