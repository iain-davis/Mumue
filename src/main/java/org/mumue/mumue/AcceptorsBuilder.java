package org.mumue.mumue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.mumue.mumue.configuration.PortConfiguration;
import org.mumue.mumue.connection.Acceptor;
import org.mumue.mumue.connection.ConnectionFactory;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.connection.ServerSocketFactory;

public class AcceptorsBuilder {
    private final ConnectionFactory connectionFactory;
    private final ConnectionManager connectionManager;
    private final ServerSocketFactory serverSocketFactory;

    @Inject
    public AcceptorsBuilder(ConnectionFactory connectionFactory, ConnectionManager connectionManager, ServerSocketFactory serverSocketFactory) {
        this.connectionFactory = connectionFactory;
        this.connectionManager = connectionManager;
        this.serverSocketFactory = serverSocketFactory;
    }

    public Collection<Acceptor> build(List<PortConfiguration> portConfigurations) {
        return portConfigurations.stream()
                .map(portConfiguration -> new Acceptor(connectionFactory, connectionManager, portConfiguration, serverSocketFactory))
                .collect(Collectors.toList());
    }
}
