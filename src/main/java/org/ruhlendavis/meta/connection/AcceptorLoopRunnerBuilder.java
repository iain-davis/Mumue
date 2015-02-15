package org.ruhlendavis.meta.connection;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.runner.InfiniteLoopRunner;

public class AcceptorLoopRunnerBuilder {
    private ConnectionAcceptorBuilder connectionAcceptorBuilder = new ConnectionAcceptorBuilder();

    public InfiniteLoopRunner build(Configuration configuration, ConnectionManager connectionManager) {
        ConnectionAcceptor connectionAcceptor = connectionAcceptorBuilder.build(configuration.getTelnetPort(), connectionManager);
        return new InfiniteLoopRunner(configuration, connectionAcceptor);
    }
}
