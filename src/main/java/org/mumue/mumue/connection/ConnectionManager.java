package org.mumue.mumue.connection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

public class ConnectionManager {
    private static final Vector<Connection> connections = new Vector<>();

    synchronized public void add(Connection connection) {
        connections.add(connection);
    }

    public Vector<Connection> getConnections() {
        return connections;
    }

    public void poseTo(long locationId, String characterName, String text, Connection... exceptions) {
        poseTo(locationId, characterName, text, Arrays.asList(exceptions));
    }

    public void poseTo(long locationId, String characterName, String text, Collection<Connection> exceptions) {
        connections.stream()
                .filter(connection -> connection.getCharacter().getLocationId() == locationId && !exceptions.contains(connection))
                .forEach(connection -> connection.getOutputQueue().push(characterName + text));
    }
}
