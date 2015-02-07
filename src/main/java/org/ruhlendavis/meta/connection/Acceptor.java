package org.ruhlendavis.meta.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.ruhlendavis.meta.runner.InfiniteLoopRunnerRunnable;

public class Acceptor extends CleanCloser implements InfiniteLoopRunnerRunnable {
    private final int port;
    private final ConnectionManager connectionManager;
    private final SocketFactory socketFactory;
    private ServerSocket serverSocket;

    public Acceptor(int port, ConnectionManager connectionManager) {
        this(port, connectionManager, new SocketFactory());
    }

    Acceptor(int port, ConnectionManager connectionManager, SocketFactory socketFactory) {
        this.port = port;
        this.connectionManager = connectionManager;
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
            connectionManager.add(clientSocket);
        } catch (IOException exception) {
            throw new RuntimeException("Error accepting client connection on port " + port, exception);
        }
    }

    @Override
    public void cleanup() {
        close(serverSocket);
    }
}
