package org.ruhlendavis.meta.connection;

import org.junit.Test;

public class ConnectionOutputSenderTest {
    private final ConnectionOutputSender connectionOutputSender = new ConnectionOutputSender(null, null);

    @Test
    public void prepare() {
        connectionOutputSender.prepare();
    }
}
