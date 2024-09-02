package org.mumue.mumue.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import jakarta.inject.Inject;

import org.mumue.mumue.configuration.PortConfiguration;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.threading.InfiniteLoopBody;

public class Acceptor implements InfiniteLoopBody {
    private final ConnectionFactory connectionFactory;
    private final ConnectionManager connectionManager;
    private final PortConfiguration portConfiguration;
    private final ServerSocketFactory serverSocketFactory;
    private ServerSocket serverSocket;

    @Inject
    public Acceptor(ConnectionFactory connectionFactory, ConnectionManager connectionManager, PortConfiguration portConfiguration, ServerSocketFactory serverSocketFactory) {
        this.connectionFactory = connectionFactory;
        this.connectionManager = connectionManager;
        this.portConfiguration = portConfiguration;
        this.serverSocketFactory = serverSocketFactory;
    }

    @Override
    public boolean prepare() {
        System.out.println(GlobalConstants.TELNET_LISTENING + portConfiguration.getPort());
        serverSocket = serverSocketFactory.createSocket(portConfiguration.getPort());
        return true;
    }

    @Override
    public boolean execute() {
        try {
            Socket clientSocket = serverSocket.accept();
            connectionManager.add(connectionFactory.create(clientSocket, portConfiguration));
        } catch (IOException exception) {
            throw new RuntimeException("Error accepting client connecting to port " + portConfiguration.getPort(), exception);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        return true;
    }

    @Override
    public boolean cleanup() {
        return true;
    }

    public PortConfiguration getPortConfiguration() {
        return portConfiguration;
    }
}
