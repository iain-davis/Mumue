package org.ruhlendavis.meta.connection;

import java.util.Collection;

import org.ruhlendavis.meta.runner.InfiniteLoopBody;

public class ConnectionInputProcessor implements InfiniteLoopBody {
    private final Collection<String> inputQueue;
    private final Collection<String> outputQueue;

    public ConnectionInputProcessor(Collection<String> inputQueue, Collection<String> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void prepare() {

    }

    @Override
    public void execute() {

    }

    @Override
    public void cleanup() {

    }
}
