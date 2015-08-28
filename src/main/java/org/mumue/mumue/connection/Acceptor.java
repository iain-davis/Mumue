package org.mumue.mumue.connection;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.threading.InfiniteLoopBody;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Acceptor extends CleanCloser implements InfiniteLoopBody {
    private final int port;
    private final ConnectionManager connectionManager;
    private final ServerSocketFactory serverSocketFactory;
    private final ConnectionFactory connectionFactory;
    private final Configuration configuration;
    private ServerSocket serverSocket;

    public Acceptor(int port, ConnectionManager connectionManager, Configuration configuration) {
        this(port, connectionManager, new ServerSocketFactory(), new ConnectionFactory(), configuration);
    }

    Acceptor(int port, ConnectionManager connectionManager, ServerSocketFactory serverSocketFactory, ConnectionFactory connectionFactory, Configuration configuration) {
        this.port = port;
        this.connectionManager = connectionManager;
        this.serverSocketFactory = serverSocketFactory;
        this.connectionFactory = connectionFactory;
        this.configuration = configuration;
    }

    @Override
    public boolean prepare() {
        System.out.println(GlobalConstants.TELNET_LISTENING + port);
        serverSocket = serverSocketFactory.createSocket(port);
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
