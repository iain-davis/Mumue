package org.mumue.mumue.connection;

import java.net.Socket;
import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.PortConfiguration;
import org.mumue.mumue.text.transformer.ColorTransformer;
import org.mumue.mumue.text.transformer.LineSeparatorTransformer;
import org.mumue.mumue.text.transformer.TransformerEngine;
import org.mumue.mumue.threading.InfiniteLoopRunnerStarter;

public class ConnectionFactory {
    private final ApplicationConfiguration applicationConfiguration;
    private final Injector injector;
    private final InfiniteLoopRunnerStarter infiniteLoopRunnerStarter;

    @Inject
    public ConnectionFactory(ApplicationConfiguration applicationConfiguration, Injector injector, InfiniteLoopRunnerStarter infiniteLoopRunnerStarter) {
        this.applicationConfiguration = applicationConfiguration;
        this.injector = injector;
        this.infiniteLoopRunnerStarter = infiniteLoopRunnerStarter;
    }

    public Connection create(Socket socket, PortConfiguration portConfiguration) {
        Connection connection = new Connection(applicationConfiguration);
        connection.setPortConfiguration(portConfiguration);

        InputReceiver inputReceiver = new InputReceiver(socket, connection.getInputQueue());
        infiniteLoopRunnerStarter.start(inputReceiver);

        TransformerEngine transformerEngine = new TransformerEngine();
        transformerEngine.add(new LineSeparatorTransformer());
        transformerEngine.add(new ColorTransformer());

        OutputSender outputSender = new OutputSender(socket, connection.getOutputQueue(), transformerEngine);
        infiniteLoopRunnerStarter.start(outputSender);

        ConnectionController controller = injector.getInstance(ConnectionController.class);
        controller.setConnection(connection);
        infiniteLoopRunnerStarter.start(controller);

        return connection;
    }
}
