package org.ruhlendavis.meta.connection;

import java.net.Socket;

public class ConnectionFactory {
    public Connection create(Socket socket) {
        Connection connection = new Connection();
        connection.initialize(socket);
        return connection;
    }
}
