package org.ruhlendavis.meta.connection;

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

@RunWith(MockitoJUnitRunner.class)
public class AcceptorTest {
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
        Acceptor acceptor = new Acceptor(port, connectionManager, socketFactory);

        acceptor.prepare();

        verify(socketFactory).createSocket(port);
    }

    @Test
    public void executeGivesAcceptedSocketToConnectionManager() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(port, connectionManager, socketFactory);

        acceptor.prepare();

        when(serverSocket.accept()).thenReturn(socket);

        acceptor.execute();

        assertThat(connectionManager.getConnections(), contains(socket));
    }

    @Test
    public void executeHandlesIOException() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(port, connectionManager, socketFactory);

        acceptor.prepare();

        when(serverSocket.accept()).thenThrow(new IOException());

        thrown.expectMessage("Error accepting client connection on port " + port);
        thrown.expect(RuntimeException.class);

        acceptor.execute();
    }

    @Test
    public void cleanupClosesServerSocket() throws IOException {
        Acceptor acceptor = new Acceptor(9999, connectionManager, socketFactory);

        acceptor.prepare();
        acceptor.cleanup();

        verify(serverSocket, atLeastOnce()).close();
    }
}
