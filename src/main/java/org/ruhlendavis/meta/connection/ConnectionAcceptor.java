package org.ruhlendavis.meta.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.ruhlendavis.meta.runner.InfiniteLoopBody;

public class ConnectionAcceptor extends CleanCloser implements InfiniteLoopBody {
    private final int port;
    private final ConnectionManager connectionManager;
    private final SocketFactory socketFactory;
    private final ConnectionFactory connectionFactory;
    private ServerSocket serverSocket;

    public ConnectionAcceptor(int port, ConnectionManager connectionManager) {
        this(port, connectionManager, new SocketFactory(), new ConnectionFactory());
    }

    ConnectionAcceptor(int port, ConnectionManager connectionManager, SocketFactory socketFactory, ConnectionFactory connectionFactory) {
        this.port = port;
        this.connectionManager = connectionManager;
        this.socketFactory = socketFactory;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void prepare() {
        serverSocket = socketFactory.createSocket(port);
    }

    @Override
    public void execute() {
        try {
            Socket clientSocket = serverSocket.accept();
            Connection connection = connectionFactory.create(clientSocket);
            connectionManager.add(connection);
        } catch (IOException exception) {
            throw new RuntimeException("Error accepting client connection on port " + port, exception);
        }
    }

    @Override
    public void cleanup() {
        close(serverSocket);
    }

    public int getPort() {
        return port;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
