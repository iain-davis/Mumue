package org.ruhlendavis.meta.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.ruhlendavis.meta.connection.CleanCloser;
import org.ruhlendavis.meta.runner.InfiniteLoopRunnerRunnable;

public class Listener extends CleanCloser implements InfiniteLoopRunnerRunnable {
    private final int port;
    private final SocketFactory socketFactory;
    private ServerSocket serverSocket;

    public Listener(int port) {
        this(port, new SocketFactory());
    }

    Listener(SocketFactory socketFactory) {
        this(9999, socketFactory);
    }

    Listener(int port, SocketFactory socketFactory) {
        this.port = port;
        this.socketFactory = socketFactory;
    }

    @Override
    public void prepare() {
        serverSocket = socketFactory.createSocket(port);
    }

    @Override
    public void execute() {
        try {
            Socket clientSocket = serverSocket.accept();
        } catch (IOException exception) {
            throw new RuntimeException("Error accepting client connection on port " + port, exception);
        }
    }

    @Override
    public void cleanup() {
        close(serverSocket);
    }
}
