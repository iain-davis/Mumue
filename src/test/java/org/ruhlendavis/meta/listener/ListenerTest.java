package org.ruhlendavis.meta.listener;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ListenerTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Mock ServerSocket serverSocket = mock(ServerSocket.class);
    @Mock SocketFactory socketFactory = mock(SocketFactory.class);

    @Before
    public void beforeEach() {
        when(socketFactory.createSocket(anyInt())).thenReturn(serverSocket);
    }

    @Test
    public void prepareUsesDefaultPort() {
        Listener listener = new Listener(socketFactory);

        listener.prepare();

        verify(socketFactory).createSocket(9999);
    }

    @Test
    public void prepareUsesSpecifiedPort() {
        int port = RandomUtils.nextInt(2048, 4096);
        Listener listener = new Listener(port, socketFactory);

        listener.prepare();

        verify(socketFactory).createSocket(port);
    }

    @Test
    public void executeHandlesIOException() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        Listener listener = new Listener(port, socketFactory);

        listener.prepare();

        when(serverSocket.accept()).thenThrow(new IOException());

        thrown.expectMessage("Error accepting client connection on port " + port);
        thrown.expect(RuntimeException.class);

        listener.execute();
    }
}
