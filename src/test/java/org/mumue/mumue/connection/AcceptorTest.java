package org.mumue.mumue.connection;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class AcceptorTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Rule public ExpectedException thrown = ExpectedException.none();
    @Mock Socket socket;
    @Mock Connection connection;
    @Mock Configuration configuration;
    @Mock ServerSocket serverSocket;
    @Mock SocketFactory socketFactory;
    @Mock ConnectionFactory connectionFactory;

    private final ConnectionManager connectionManager = new ConnectionManager();

    @Before
    public void beforeEach() throws IOException {
        when(socketFactory.createSocket(anyInt())).thenReturn(serverSocket);
        when(serverSocket.accept()).thenReturn(socket);
        when(connectionFactory.create(socket, configuration)).thenReturn(connection);
    }

    @Test
    public void prepareUsesSpecifiedPort() {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(port, connectionManager, socketFactory, connectionFactory, configuration);

        acceptor.prepare();

        verify(socketFactory).createSocket(port);
    }

    @Test
    public void prepareReturnsTrue() {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(port, connectionManager, socketFactory, connectionFactory, configuration);

        assertTrue(acceptor.prepare());
    }

    @Test
    public void executeCreatesConnectionUsingSocket() {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(port, connectionManager, socketFactory, connectionFactory, configuration);

        acceptor.prepare();
        acceptor.execute();

        verify(connectionFactory).create(socket, configuration);
    }

    @Test
    public void executeReturnsTrue() {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(port, connectionManager, socketFactory, connectionFactory, configuration);

        acceptor.prepare();

        assertTrue(acceptor.execute());
    }

    @Test
    public void executeGivesConnectionToConnectionManager() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(port, connectionManager, socketFactory, connectionFactory, configuration);

        acceptor.prepare();
        acceptor.execute();

        assertThat(connectionManager.getConnections(), hasItem(connection));
    }

    @Test
    public void executeHandlesIOException() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(port, connectionManager, socketFactory, connectionFactory, configuration);

        acceptor.prepare();

        when(serverSocket.accept()).thenThrow(new IOException());

        thrown.expectMessage("Error accepting client connection on port " + port);
        thrown.expect(RuntimeException.class);

        acceptor.execute();
    }

    @Test
    public void cleanupClosesServerSocket() throws IOException {
        Acceptor acceptor = new Acceptor(9999, connectionManager, socketFactory, connectionFactory, configuration);

        acceptor.prepare();
        acceptor.cleanup();

        verify(serverSocket, atLeastOnce()).close();
    }

    @Test
    public void cleanupReturnsTrue() throws IOException {
        Acceptor acceptor = new Acceptor(9999, connectionManager, socketFactory, connectionFactory, configuration);

        acceptor.prepare();
        assertTrue(acceptor.cleanup());
    }
}
