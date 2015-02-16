package org.ruhlendavis.meta.connection;

import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ruhlendavis.meta.runner.InfiniteLoopRunnerStarter;

public class Connection {
    private InfiniteLoopRunnerStarter infiniteLoopRunnerStarter = new InfiniteLoopRunnerStarter();

    public void initialize(Socket socket) {
        Collection<String> inputQueue = new ConcurrentLinkedQueue<>();
        Collection<String> outputQueue = new ConcurrentLinkedQueue<>();

        InputReceiver inputReceiver = new InputReceiver(socket, inputQueue);
        infiniteLoopRunnerStarter.start(inputReceiver);

        InputProcessor inputProcessor = new InputProcessor(inputQueue, outputQueue);
        infiniteLoopRunnerStarter.start(inputProcessor);

        OutputSender outputSender = new OutputSender(socket, outputQueue);
        infiniteLoopRunnerStarter.start(outputSender);
    }
}
