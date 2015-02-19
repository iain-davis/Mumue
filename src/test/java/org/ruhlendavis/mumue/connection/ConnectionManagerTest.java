package org.ruhlendavis.mumue.connection;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import org.junit.Test;

public class ConnectionManagerTest {
    private final ConnectionManager connectionManager = new ConnectionManager();

    @Test
    public void addSocket() {
        Connection connection = new Connection();
        connectionManager.add(connection);

        assertThat(connectionManager.getConnections(), contains(connection));
    }
}
