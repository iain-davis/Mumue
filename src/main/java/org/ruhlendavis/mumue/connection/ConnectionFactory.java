package org.ruhlendavis.mumue.connection;

import java.net.Socket;

import org.ruhlendavis.mumue.configuration.Configuration;

public class ConnectionFactory {
    private ConnectionInitializer connectionInitializer = new ConnectionInitializer();

    public Connection create(Socket socket, Configuration configuration) {
        Connection connection = new Connection();
        connectionInitializer.initialize(socket, connection, configuration);
        return connection;
    }
}
