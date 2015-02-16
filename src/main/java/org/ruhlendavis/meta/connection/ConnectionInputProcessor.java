package org.ruhlendavis.meta.connection;

import java.util.Collection;
import java.util.Optional;

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
        if (inputQueue.isEmpty()) {
            return;
        }
        String line = inputQueue.stream().findFirst().get();
        inputQueue.remove(line);
        outputQueue.add(line);
    }

    @Override
    public void cleanup() {

    }
}
