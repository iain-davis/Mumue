package org.ruhlendavis.meta.connection;

import org.junit.Test;

public class OutputSenderTest {
    private final OutputSender outputSender = new OutputSender(null, null);

    @Test
    public void prepare() {
        outputSender.prepare();
    }
}
