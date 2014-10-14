package org.ruhlendavis.meta.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Listener implements Runnable {
    private SocketFactory socketFactory = new SocketFactory();
    private ThreadFactory threadFactory = new ThreadFactory();
    private ServerSocket serverSocket;
    private int port = 9999;
    private boolean isRunning = true;
    private Vector<Thread> connections = new Vector();

    public Listener() {
    }

    @Override
    public void run() {
        serverSocket = socketFactory.createSocket(port);
        while (isRunning) {
            waitForConnection();
        }
    }

    protected void waitForConnection() {
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            Connection connection = new Connection().withSocket(clientSocket);
            Thread client = threadFactory.createThread(connection, "client connection");
            connections.add(client);
            client.start();
        } catch (IOException exception) {
            if (!isRunning) {
                return;
            }
            throw new RuntimeException("Error accepting client connection", exception);
        }
    }

    public Listener withPort(int port) {
        this.port = port;
        return this;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stop() {
        isRunning = false;
    }

    public int getConnectionCount() {
        return connections.size();
    }
}
