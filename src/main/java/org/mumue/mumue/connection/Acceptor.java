package org.mumue.mumue.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.inject.Inject;

import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.threading.InfiniteLoopBody;

public class Acceptor implements InfiniteLoopBody {
    private final ServerSocketFactory serverSocketFactory;
    private final ConnectionFactory connectionFactory;
    private final ConnectionManager connectionManager;
    private ServerSocket serverSocket;
    private int port;

    @Inject
    public Acceptor(ServerSocketFactory serverSocketFactory, ConnectionFactory connectionFactory, ConnectionManager connectionManager) {
        this.serverSocketFactory = serverSocketFactory;
        this.connectionFactory = connectionFactory;
        this.connectionManager = connectionManager;
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
            Connection connection = connectionFactory.create(clientSocket);
            connectionManager.add(connection);
        } catch (IOException exception) {
            throw new RuntimeException("Error accepting client connecting to port " + port, exception);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        return true;
    }

    @Override
    public boolean cleanup() {
        return true;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
