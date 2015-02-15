package org.ruhlendavis.meta.connection;

import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ruhlendavis.meta.ThreadFactory;
import org.ruhlendavis.meta.configuration.ConfigurationProvider;
import org.ruhlendavis.meta.runner.InfiniteLoopRunner;

public class Connection {
    private ThreadFactory threadFactory = new ThreadFactory();

    public void initialize(Socket socket) {
        Collection<String> inputQueue = new ConcurrentLinkedQueue<>();

        InfiniteLoopRunner inputReceiverLoop = new InfiniteLoopRunner(ConfigurationProvider.get(), new ConnectionInputReceiver(socket, inputQueue));
        Thread thread = threadFactory.create(inputReceiverLoop);
        thread.start();
    }
}
