package org.ruhlendavis.mumue.connection;

import java.net.Socket;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.text.transformer.ColorTransformer;
import org.ruhlendavis.mumue.threading.InfiniteLoopRunnerStarter;
import org.ruhlendavis.mumue.text.transformer.LineSeparatorTransformer;
import org.ruhlendavis.mumue.text.transformer.TransformerEngine;

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
