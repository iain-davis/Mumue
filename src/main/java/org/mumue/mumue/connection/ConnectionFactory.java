package org.mumue.mumue.connection;

import java.net.Socket;
import javax.inject.Inject;

import com.google.inject.Injector;

public class ConnectionFactory {
    private final Injector injector;
    private final ConnectionInitializer connectionInitializer;

    @Inject
    public ConnectionFactory(Injector injector, ConnectionInitializer connectionInitializer) {
        this.injector = injector;
        this.connectionInitializer = connectionInitializer;
    }

    public Connection create(Socket socket) {
        Connection connection = injector.getInstance(Connection.class);
        connectionInitializer.initialize(socket, connection);
        return connection;
    }
}
