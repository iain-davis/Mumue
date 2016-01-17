package org.mumue.mumue.connection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mumue.mumue.configuration.ApplicationConfiguration;

public class AcceptorTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private final ServerSocket serverSocket = mock(ServerSocket.class);
    private final ServerSocketFactory serverSocketFactory = mock(ServerSocketFactory.class);
    private final ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    private final Socket clientSocket = mock(Socket.class);
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final Connection connection = new Connection(configuration);

    @Before
    public void beforeEach() {
        connectionManager.getConnections().clear();
    }

    @Test
    public void usesSpecifiedPort() {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(serverSocketFactory, connectionFactory, connectionManager);
        acceptor.setPort(port);

        acceptor.prepare();

        verify(serverSocketFactory).createSocket(port);
    }

    @Test
    public void createsConnectionFromAcceptedSocket() throws IOException {
        Acceptor acceptor = new Acceptor(serverSocketFactory, connectionFactory, connectionManager);
        int port = new Random().nextInt(65535);
        acceptor.setPort(port);

        when(serverSocketFactory.createSocket(port)).thenReturn(serverSocket);
        when(serverSocket.accept()).thenReturn(clientSocket);

        acceptor.prepare();
        acceptor.execute();

        verify(connectionFactory).create(clientSocket);
    }

    @Test
    public void addsNewConnectionToConnectionManager() throws IOException {
        Acceptor acceptor = new Acceptor(serverSocketFactory, connectionFactory, connectionManager);
        int port = new Random().nextInt(65535);
        acceptor.setPort(port);

        when(serverSocketFactory.createSocket(port)).thenReturn(serverSocket);
        when(serverSocket.accept()).thenReturn(clientSocket);
        when(connectionFactory.create(clientSocket)).thenReturn(connection);

        acceptor.prepare();
        acceptor.execute();

        assertThat(connectionManager.getConnections().size(), equalTo(1));
        assertSame(connectionManager.getConnections().firstElement(), connection);
    }

    @Test
    public void executeHandlesIOException() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(serverSocketFactory, connectionFactory, connectionManager);
        acceptor.setPort(port);

        when(serverSocketFactory.createSocket(port)).thenReturn(serverSocket);
        when(serverSocket.accept()).thenThrow(new IOException());

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Error accepting client connecting to port " + port);

        acceptor.prepare();
        acceptor.execute();
    }
}