package org.mumue.mumue.connection;

import java.net.Socket;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.text.transformer.ColorTransformer;
import org.mumue.mumue.text.transformer.LineSeparatorTransformer;
import org.mumue.mumue.text.transformer.TransformerEngine;
import org.mumue.mumue.threading.InfiniteLoopRunnerStarter;
import sun.security.krb5.internal.ccache.CCacheInputStream;

import javax.inject.Inject;

public class ConnectionInitializer {
    private final Injector injector;
    private final InfiniteLoopRunnerStarter infiniteLoopRunnerStarter;

    @Inject
    public ConnectionInitializer(Injector injector, InfiniteLoopRunnerStarter infiniteLoopRunnerStarter) {
        this.injector = injector;
        this.infiniteLoopRunnerStarter = infiniteLoopRunnerStarter;
    }

    public void initialize(Socket socket, Connection connection) {
        InputReceiver inputReceiver = new InputReceiver(socket, connection.getInputQueue());
        infiniteLoopRunnerStarter.start(inputReceiver);

        ConnectionController controller = injector.getInstance(ConnectionController.class);
        controller.setConnection(connection);
        infiniteLoopRunnerStarter.start(controller);

        TransformerEngine transformerEngine = new TransformerEngine();
        transformerEngine.add(new LineSeparatorTransformer());
        transformerEngine.add(new ColorTransformer());

        OutputSender outputSender = new OutputSender(socket, connection.getOutputQueue(), transformerEngine);
        infiniteLoopRunnerStarter.start(outputSender);
    }
}
