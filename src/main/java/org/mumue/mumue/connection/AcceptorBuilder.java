package org.mumue.mumue.connection;

import org.mumue.mumue.configuration.Configuration;

public class AcceptorBuilder {
    public Acceptor build(int port, ConnectionManager connectionManager, Configuration configuration) {
        return new Acceptor(port, connectionManager, configuration);
    }
}
