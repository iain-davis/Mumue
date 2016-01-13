package org.mumue.mumue.connection;

import org.mumue.mumue.configuration.Configuration;

import javax.inject.Inject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RunnableAcceptor implements Runnable {
    private final ServerSocket serverSocket;
    private final ConnectionFactory connectionFactory;
    private final Configuration configuration;
    private final ConnectionManager connectionManager;
    private final int port;

    @Inject
    public RunnableAcceptor(ServerSocketFactory factory, ConnectionFactory connectionFactory, Configuration configuration, ConnectionManager connectionManager, int port) {
        this.connectionFactory = connectionFactory;
        this.configuration = configuration;
        this.connectionManager = connectionManager;
        this.serverSocket = factory.createSocket(port);
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = serverSocket.accept();
            Connection connection = connectionFactory.create(clientSocket, configuration);
            connectionManager.add(connection);
        } catch (IOException exception) {
            throw new RuntimeException("Error accepting client connecting to port " + port, exception);
        }
    }
}
