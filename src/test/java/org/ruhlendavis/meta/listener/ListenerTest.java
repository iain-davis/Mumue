package org.ruhlendavis.meta.listener;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ListenerTest {
    private ServerSocket serverSocket = mock(ServerSocket.class);
    private Thread clientThread = mock(Thread.class);

    @Mock
    private SocketFactory socketFactory;
    @Mock
    private ThreadFactory threadFactory;
    @InjectMocks
    private Listener listener = new Listener();

    @Before
    public void beforeEach() {
        when(socketFactory.createSocket(9999)).thenReturn(serverSocket);
        when(threadFactory.createThread(any(Connection.class), anyString())).thenReturn(clientThread);
    }

    @Test
    public void runUsesDefaultPort() {
        listener.stop();
        listener.run();
        verify(socketFactory).createSocket(9999);
    }

    @Test
    public void runUsesSpecifiedPort() {
        int port = RandomUtils.nextInt(2048, 4096);
        listener.withPort(port);
        listener.stop();
        listener.run();
        verify(socketFactory).createSocket(port);
    }

    @Test
    public void waitForConnectionCreatesThread() throws IOException {
        when(serverSocket.accept()).thenReturn(new Socket());
        listener.waitForConnection();
        assertEquals(1, listener.getConnectionCount());
    }

    @Test
    public void waitForConnectionHandlesIOExceptionWhileRunning() throws IOException {
        when(serverSocket.accept()).thenThrow(new IOException());
        assertEquals(0, listener.getConnectionCount());
    }

    @Test
    public void waitForConnectionHandlesIOExceptionWhileStopped() throws IOException {
        when(serverSocket.accept()).thenThrow(new IOException());
        listener.stop();
        assertEquals(0, listener.getConnectionCount());
    }
}
