package org.ruhlendavis.meta.listener;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SocketFactoryTest {
    SocketFactory builder = new SocketFactory();
    @Test
    public void createSocketSetsThePort() {
        assertEquals(9999, builder.createSocket(9999).getLocalPort());
    }
}
