package org.ruhlendavis.meta.connection;

import java.net.Socket;
import java.util.Collection;

import org.ruhlendavis.meta.runner.InfiniteLoopBody;

public class OutputSender implements InfiniteLoopBody {
    private final Socket socket;
    private final Collection<String> outputQueue;

    public OutputSender(Socket socket, Collection<String> outputQueue) {
        this.socket = socket;
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
