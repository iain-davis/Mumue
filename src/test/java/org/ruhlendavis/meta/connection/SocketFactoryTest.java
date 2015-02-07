package org.ruhlendavis.meta.connection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SocketFactoryTest {
    private final SocketFactory builder = new SocketFactory();
    @Test
    public void createSocketSetsThePort() {
        assertEquals(9999, builder.createSocket(9999).getLocalPort());
    }
}
