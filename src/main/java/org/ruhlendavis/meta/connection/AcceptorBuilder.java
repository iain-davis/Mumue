package org.ruhlendavis.meta.connection;

public class AcceptorBuilder {
    public Acceptor build(int port, ConnectionManager connectionManager) {
        return new Acceptor(port, connectionManager);
    }
}
