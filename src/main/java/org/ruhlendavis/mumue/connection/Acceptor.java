package org.ruhlendavis.mumue.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.runner.InfiniteLoopBody;

public class Acceptor extends CleanCloser implements InfiniteLoopBody {
    private final int port;
    private final ConnectionManager connectionManager;
    private final SocketFactory socketFactory;
    private final ConnectionFactory connectionFactory;
    private final Configuration configuration;
    private ServerSocket serverSocket;

    public Acceptor(int port, ConnectionManager connectionManager, Configuration configuration) {
        this(port, connectionManager, new SocketFactory(), new ConnectionFactory(), configuration);
    }

    Acceptor(int port, ConnectionManager connectionManager, SocketFactory socketFactory, ConnectionFactory connectionFactory, Configuration configuration) {
        this.port = port;
        this.connectionManager = connectionManager;
        this.socketFactory = socketFactory;
        this.connectionFactory = connectionFactory;
        this.configuration = configuration;
    }

    @Override
    public void prepare() {
        serverSocket = socketFactory.createSocket(port);
    }

    @Override
    public void execute() {
        try {
            Socket clientSocket = serverSocket.accept();
            Connection connection = connectionFactory.create(clientSocket, configuration);
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
