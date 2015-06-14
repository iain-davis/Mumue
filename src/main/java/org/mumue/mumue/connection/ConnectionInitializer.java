package org.mumue.mumue.connection;

import java.net.Socket;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.text.transformer.ColorTransformer;
import org.mumue.mumue.text.transformer.LineSeparatorTransformer;
import org.mumue.mumue.text.transformer.TransformerEngine;
import org.mumue.mumue.threading.InfiniteLoopRunnerStarter;

public class ConnectionInitializer {
    private InfiniteLoopRunnerStarter infiniteLoopRunnerStarter = new InfiniteLoopRunnerStarter();

    public void initialize(Socket socket, Connection connection, Configuration configuration) {
        InputReceiver inputReceiver = new InputReceiver(socket, connection.getInputQueue());
        infiniteLoopRunnerStarter.start(inputReceiver);

        ConnectionController controller = new ConnectionController(connection, configuration);
        infiniteLoopRunnerStarter.start(controller);

        TransformerEngine transformerEngine = new TransformerEngine();
        transformerEngine.add(new LineSeparatorTransformer());
        transformerEngine.add(new ColorTransformer());

        OutputSender outputSender = new OutputSender(socket, connection.getOutputQueue(), transformerEngine);
        infiniteLoopRunnerStarter.start(outputSender);
    }
}
