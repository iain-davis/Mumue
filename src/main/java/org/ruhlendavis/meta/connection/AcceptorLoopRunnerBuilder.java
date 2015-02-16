package org.ruhlendavis.meta.connection;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.runner.InfiniteLoopRunner;

public class AcceptorLoopRunnerBuilder {
    private AcceptorBuilder acceptorBuilder = new AcceptorBuilder();

    public InfiniteLoopRunner build(Configuration configuration, ConnectionManager connectionManager) {
        Acceptor acceptor = acceptorBuilder.build(configuration.getTelnetPort(), connectionManager);
        return new InfiniteLoopRunner(configuration, acceptor);
    }
}
