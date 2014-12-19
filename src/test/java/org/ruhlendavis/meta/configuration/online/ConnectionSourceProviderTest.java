package org.ruhlendavis.meta.configuration.online;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ConnectionSourceProviderTest {
    private final ConnectionSourceProvider connectionSourceProvider = new ConnectionSourceProvider();

    @Test
    public void getReturnsConnectionSource() {
        assertNotNull(connectionSourceProvider.get("jdbc:h2:mem", "a", "a"));
    }
}