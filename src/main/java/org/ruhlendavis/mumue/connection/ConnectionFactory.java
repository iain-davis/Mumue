package org.ruhlendavis.mumue.connection;

import java.net.Socket;

import org.ruhlendavis.mumue.configuration.Configuration;

public class ConnectionFactory {
    public Connection create(Socket socket, Configuration configuration) {
        Connection connection = new Connection();
        ConnectionInitializer connectionInitializer = new ConnectionInitializer();
        connectionInitializer.initialize(socket, connection, configuration);
        return connection;
    }
}
