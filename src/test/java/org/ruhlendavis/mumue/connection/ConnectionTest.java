package org.ruhlendavis.mumue.connection;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ConnectionTest {
    Connection connection = new Connection();

    @Test
    public void inputQueueNotNull() {
        assertNotNull(connection.getInputQueue());
    }
    @Test
    public void outputQueueNotNull() {
        assertNotNull(connection.getOutputQueue());
    }
    @Test
    public void characterNotNull() {
        assertNotNull(connection.getCharacter());
    }
}
