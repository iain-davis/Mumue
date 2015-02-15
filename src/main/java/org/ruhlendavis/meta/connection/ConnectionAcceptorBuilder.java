package org.ruhlendavis.meta.connection;

public class ConnectionAcceptorBuilder {
    public ConnectionAcceptor build(int port, ConnectionManager connectionManager) {
        return new ConnectionAcceptor(port, connectionManager);
    }
}
