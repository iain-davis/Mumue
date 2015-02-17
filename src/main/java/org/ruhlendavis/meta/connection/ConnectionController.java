package org.ruhlendavis.meta.connection;

import org.ruhlendavis.meta.runner.InfiniteLoopBody;

public class ConnectionController implements InfiniteLoopBody {
    private final TextQueue inputQueue;
    private final TextQueue outputQueue;

    public ConnectionController(TextQueue inputQueue, TextQueue outputQueue) {
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
        String text = inputQueue.pop();
        outputQueue.push(text);
    }

    @Override
    public void cleanup() {

    }
}
