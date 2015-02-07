package org.ruhlendavis.meta.connection;

import java.net.Socket;
import java.util.Vector;

public class ConnectionManager {
    Vector<Socket> connections = new Vector<>();

    public void add(Socket socket) {
        connections.add(socket);
    }

    public Vector<Socket> getConnections() {
        return connections;
    }
}
