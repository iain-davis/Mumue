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
    public boolean prepare() {
        serverSocket = socketFactory.createSocket(port);
        return true;
    }

    @Override
    public boolean execute() {
        try {
            Socket clientSocket = serverSocket.accept();
            Connection connection = connectionFactory.create(clientSocket, configuration);
            connectionManager.add(connection);
            return true;
        } catch (IOException exception) {
            throw new RuntimeException("Error accepting client connection on port " + port, exception);
        }
    }

    @Override
    public boolean cleanup() {
        close(serverSocket);
        return true;
    }

    public int getPort() {
        return port;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
