package org.mumue.mumue.connection;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.threading.InfiniteLoopRunner;

import javax.inject.Inject;

public class AcceptorLoopRunnerBuilder {
    private final AcceptorBuilder acceptorBuilder;

    @Inject
    public AcceptorLoopRunnerBuilder(AcceptorBuilder acceptorBuilder) {
        this.acceptorBuilder = acceptorBuilder;
    }

    public InfiniteLoopRunner build(Configuration configuration, ConnectionManager connectionManager) {
        Acceptor acceptor = acceptorBuilder.build(configuration.getTelnetPort(), connectionManager, configuration);
        return new InfiniteLoopRunner(acceptor);
    }
}
