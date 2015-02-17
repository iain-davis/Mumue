package org.ruhlendavis.meta.connection;

import org.ruhlendavis.meta.configuration.Configuration;

public class AcceptorBuilder {
    public Acceptor build(int port, ConnectionManager connectionManager, Configuration configuration) {
        return new Acceptor(port, connectionManager, configuration);
    }
}
