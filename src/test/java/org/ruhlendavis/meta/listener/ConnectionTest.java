package org.ruhlendavis.meta.listener;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConnectionTest {
    @Test
    public void buildUnauthenticatedCommandList() {
        Connection connection = new Connection();
        connection.run();
        assertEquals(2, connection.getInterpreter().getCommands().size());
    }
}
