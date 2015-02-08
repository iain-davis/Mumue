package org.ruhlendavis.meta.connection;

import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ruhlendavis.meta.ThreadFactory;
import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.runner.InfiniteLoopRunner;

public class Connection {
    private ThreadFactory threadFactory = new ThreadFactory();

    public void initialize(Configuration configuration, Socket socket) {
        Collection<String> inputQueue = new ConcurrentLinkedQueue<>();

        InfiniteLoopRunner inputReceiverLoop = new InfiniteLoopRunner(configuration, new ConnectionInputReceiver(socket, inputQueue));
        threadFactory.create(inputReceiverLoop);
    }
}
