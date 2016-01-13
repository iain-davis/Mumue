package org.mumue.mumue.connection;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.threading.InfiniteLoopBody;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Acceptor implements InfiniteLoopBody {
    private final ServerSocket serverSocket;
    private final ConnectionFactory connectionFactory;
    private final Configuration configuration;
    private final ConnectionManager connectionManager;
    private final int port;

    public Acceptor(ServerSocketFactory factory, ConnectionFactory connectionFactory, Configuration configuration, ConnectionManager connectionManager, int port) {
        this.connectionFactory = connectionFactory;
        this.configuration = configuration;
        this.connectionManager = connectionManager;
        this.serverSocket = factory.createSocket(port);
        this.port = port;
    }

    @Override
    public boolean prepare() {
        return true;
    }

    @Override
    public boolean execute() {
        try {
            Socket clientSocket = serverSocket.accept();
            Connection connection = connectionFactory.create(clientSocket, configuration);
            connectionManager.add(connection);
        } catch (IOException exception) {
            throw new RuntimeException("Error accepting client connecting to port " + port, exception);
        }
        return true;
    }

    @Override
    public boolean cleanup() {
        return true;
    }
}
