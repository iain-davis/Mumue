package org.mumue.mumue.connection;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.configuration.Configuration;

public class ConnectionManagerTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    private final ConnectionManager connectionManager = new ConnectionManager();

    @Test
    public void addSocket() {
        Connection connection = new Connection(configuration);
        connectionManager.add(connection);

        assertThat(connectionManager.getConnections(), hasItem(connection));
    }
}
