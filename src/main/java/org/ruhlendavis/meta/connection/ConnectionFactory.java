package org.ruhlendavis.meta.connection;

import java.net.Socket;

import org.ruhlendavis.meta.configuration.Configuration;

public class ConnectionFactory {
    public Connection create(Socket socket, Configuration configuration) {
        Connection connection = new Connection();
        ConnectionInitializer connectionInitializer = new ConnectionInitializer();
        connectionInitializer.initialize(socket, connection, configuration);
        return connection;
    }
}
