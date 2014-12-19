package org.ruhlendavis.meta.listener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    @Mock private Socket socket;

    @Before
    public void beforeEach() throws IOException {
        when(socket.getOutputStream()).thenReturn(output);
    }

    @Test
    public void buildUnauthenticatedCommandList() {
        Connection connection = new Connection().withSocket(socket);
        connection.run();
        assertEquals(2, connection.getInterpreter().getCommands().size());
    }

    @Test
    public void connectionUsesProvidedSocket() {
        Connection connection = new Connection().withSocket(socket);
        connection.run();
        assertTrue(StringUtils.isNotBlank(output.toString()));
    }

    @Test
    public void sendWelcomeToSocket() {
        Connection connection = new Connection().withSocket(socket);
        connection.run();
        assertEquals("", output.toString());
    }
}
