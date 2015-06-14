package org.mumue.mumue.connection;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.threading.InfiniteLoopRunner;

public class AcceptorLoopRunnerBuilder {
    private AcceptorBuilder acceptorBuilder = new AcceptorBuilder();

    public InfiniteLoopRunner build(Configuration configuration, ConnectionManager connectionManager) {
        Acceptor acceptor = acceptorBuilder.build(configuration.getTelnetPort(), connectionManager, configuration);
        return new InfiniteLoopRunner(acceptor);
    }
}
