package org.ruhlendavis.meta.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.CleanCloser;
import org.ruhlendavis.meta.connection.ConnectionInputReceiver;

public class Listener extends CleanCloser implements Runnable {
    private SocketFactory socketFactory = new SocketFactory();
    private ThreadFactory threadFactory = new ThreadFactory();
    private ServerSocket serverSocket;
    private int port = 9999;
    private boolean running = true;
    private Vector<Thread> connections = new Vector<>();
    private Configuration configuration;

    @Override
    public void run() {
        serverSocket = socketFactory.createSocket(port);
        while (isRunning()) {
            waitForConnection();
        }
        close(serverSocket);
    }

    protected void waitForConnection() {
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            ConnectionInputReceiver connectionInputReceiver = new ConnectionInputReceiver().withSocket(clientSocket).withConfiguration(configuration);
            Thread client = threadFactory.createThread(connectionInputReceiver, "client connection");
            connections.add(client);
            client.start();
        } catch (IOException exception) {
            if (running) {
                throw new RuntimeException("Error accepting client connection", exception);
            }
        }
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void stop() {
        running = false;
    }

    public int getConnectionCount() {
        return connections.size();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
