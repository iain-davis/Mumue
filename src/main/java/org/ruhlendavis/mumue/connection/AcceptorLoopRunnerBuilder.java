package org.ruhlendavis.mumue.connection;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.runner.InfiniteLoopRunner;

public class AcceptorLoopRunnerBuilder {
    private AcceptorBuilder acceptorBuilder = new AcceptorBuilder();

    public InfiniteLoopRunner build(Configuration configuration, ConnectionManager connectionManager) {
        Acceptor acceptor = acceptorBuilder.build(configuration.getTelnetPort(), connectionManager, configuration);
        return new InfiniteLoopRunner(configuration, acceptor);
    }
}
