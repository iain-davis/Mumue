package org.ruhlendavis.meta.listener;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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

import org.ruhlendavis.meta.connection.ConnectionInputReceiver;

@RunWith(MockitoJUnitRunner.class)
public class ListenerTest {
    private ServerSocket serverSocket = mock(ServerSocket.class);
    private Thread clientThread = mock(Thread.class);

    @Mock private SocketFactory socketFactory;
    @Mock private ThreadFactory threadFactory;
    @InjectMocks private Listener listener;

    @Before
    public void beforeEach() {
        when(socketFactory.createSocket(anyInt())).thenReturn(serverSocket);
        when(threadFactory.createThread(any(ConnectionInputReceiver.class), anyString())).thenReturn(clientThread);
    }

    @Test
    public void prepareUsesDefaultPort() {
        listener.prepare();
        verify(socketFactory).createSocket(9999);
    }

    @Test
    public void prepareUsesSpecifiedPort() {
        int port = RandomUtils.nextInt(2048, 4096);
        listener.setPort(port);
        listener.prepare();
        verify(socketFactory).createSocket(port);
    }

    @Test
    public void runCreatesThread() throws IOException {
        when(serverSocket.accept()).thenReturn(new Socket());
        listener.run();
        assertEquals(1, listener.getConnectionCount());
    }

    @Test
    public void runHandlesIOExceptionWhileRunning() throws IOException {
        when(serverSocket.accept()).thenThrow(new IOException());
        assertEquals(0, listener.getConnectionCount());
    }

    @Test
    public void runHandlesIOExceptionWhileStopped() throws IOException {
        when(serverSocket.accept()).thenThrow(new IOException());
        assertEquals(0, listener.getConnectionCount());
    }
}
