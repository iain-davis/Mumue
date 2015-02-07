package org.ruhlendavis.meta.listener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.ConnectionManager;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionAcceptorTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    private final ConnectionManager connectionManager = new ConnectionManager();
    @Mock Socket socket;
    @Mock ServerSocket serverSocket;
    @Mock SocketFactory socketFactory;

    @Before
    public void beforeEach() {
        when(socketFactory.createSocket(anyInt())).thenReturn(serverSocket);
    }

    @Test
    public void prepareUsesSpecifiedPort() {
        int port = RandomUtils.nextInt(2048, 4096);
        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(port, connectionManager, socketFactory);

        connectionAcceptor.prepare();

        verify(socketFactory).createSocket(port);
    }

    @Test
    public void executeGivesAcceptedSocketToConnectionManager() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(port, connectionManager, socketFactory);

        connectionAcceptor.prepare();

        when(serverSocket.accept()).thenReturn(socket);

        connectionAcceptor.execute();

        assertThat(connectionManager.getConnections(), contains(socket));
    }

    @Test
    public void executeHandlesIOException() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(port, connectionManager, socketFactory);

        connectionAcceptor.prepare();

        when(serverSocket.accept()).thenThrow(new IOException());

        thrown.expectMessage("Error accepting client connection on port " + port);
        thrown.expect(RuntimeException.class);

        connectionAcceptor.execute();
    }

    @Test
    public void cleanupClosesServerSocket() throws IOException {
        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(9999, connectionManager, socketFactory);

        connectionAcceptor.prepare();
        connectionAcceptor.cleanup();

        verify(serverSocket, atLeastOnce()).close();
    }
}
