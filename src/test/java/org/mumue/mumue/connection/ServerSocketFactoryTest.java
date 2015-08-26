package org.mumue.mumue.connection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ServerSocketFactoryTest {
    private final ServerSocketFactory builder = new ServerSocketFactory();
    @Test
    public void createSocketSetsThePort() {
        assertEquals(9999, builder.createSocket(9999).getLocalPort());
    }
}
