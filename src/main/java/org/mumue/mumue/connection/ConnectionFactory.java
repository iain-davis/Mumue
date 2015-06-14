package org.mumue.mumue.connection;

import java.net.Socket;

import org.mumue.mumue.configuration.Configuration;

public class ConnectionFactory {
    private ConnectionInitializer connectionInitializer = new ConnectionInitializer();

    public Connection create(Socket socket, Configuration configuration) {
        Connection connection = new Connection(configuration);
        connectionInitializer.initialize(socket, connection, configuration);
        return connection;
    }
}
