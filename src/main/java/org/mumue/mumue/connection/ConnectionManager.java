package org.mumue.mumue.connection;

import java.util.Vector;

public class ConnectionManager {
    private static final Vector<Connection> connections = new Vector<>();

    synchronized public void add(Connection connection) {
        connections.add(connection);
    }

    public Vector<Connection> getConnections() {
        return connections;
    }
}
