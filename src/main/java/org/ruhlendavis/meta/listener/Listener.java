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
    private boolean running = true;
    private Vector<Thread> connections = new Vector();

    public Listener() {
    }

    @Override
    public void run() {
        serverSocket = socketFactory.createSocket(port);
        while (isRunning()) {
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
            if (!running) {
                return;
            }
            throw new RuntimeException("Error accepting client connection", exception);
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

    public int getPort() {
        return port;
    }
}
