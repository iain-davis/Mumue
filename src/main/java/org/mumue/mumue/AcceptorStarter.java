package org.mumue.mumue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import javax.inject.Inject;

import org.mumue.mumue.connection.Acceptor;
import org.mumue.mumue.threading.InfiniteLoopRunner;

public class AcceptorStarter {
    private final ExecutorService executorService;

    @Inject
    public AcceptorStarter(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Collection<Future<?>> start(Collection<Acceptor> acceptors) {
        return acceptors.stream()
                .map(acceptor -> executorService.submit(new InfiniteLoopRunner(acceptor)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
