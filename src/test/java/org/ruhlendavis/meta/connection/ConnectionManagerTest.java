package org.ruhlendavis.meta.connection;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.net.Socket;

import org.junit.Test;

public class ConnectionManagerTest {
    private final ConnectionManager connectionManager = new ConnectionManager();

    @Test
    public void addSocket() {
        Socket socket = new Socket();
        connectionManager.add(socket);

        assertThat(connectionManager.getConnections(), contains(socket));
    }
}
