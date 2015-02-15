package org.ruhlendavis.meta.connection;

import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ruhlendavis.meta.runner.InfiniteLoopRunnerStarter;

public class Connection {
    private InfiniteLoopRunnerStarter infiniteLoopRunnerStarter = new InfiniteLoopRunnerStarter();

    public void initialize(Socket socket) {
        Collection<String> inputQueue = new ConcurrentLinkedQueue<>();
        ConnectionInputReceiver inputReceiver = new ConnectionInputReceiver(socket, inputQueue);
        infiniteLoopRunnerStarter.start(inputReceiver);
    }
}
