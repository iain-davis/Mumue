package org.ruhlendavis.mumue.connection;

import java.util.Vector;

public class ConnectionManager {
    Vector<Connection> connections = new Vector<>();

    public void add(Connection connection) {
        connections.add(connection);
    }

    public Vector<Connection> getConnections() {
        return connections;
    }
}
