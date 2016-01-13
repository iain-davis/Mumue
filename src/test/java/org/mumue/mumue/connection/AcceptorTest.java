package org.mumue.mumue.connection;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mumue.mumue.configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptorTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private final ServerSocket serverSocket = mock(ServerSocket.class);
    private final MockServerSocketFactory serverSocketFactory = new MockServerSocketFactory(serverSocket);
    private final MockConnectionFactory connectionFactory = new MockConnectionFactory();
    private final Socket clientSocket = mock(Socket.class);
    private final Configuration configuration = mock(Configuration.class);
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final Connection connection = new Connection(configuration);

    @Before
    public void beforeEach() {
        connectionManager.getConnections().clear();
    }

    @Test
    public void usesSpecifiedPort() {
        int port = RandomUtils.nextInt(2048, 4096);
        new Acceptor(serverSocketFactory, connectionFactory, configuration, connectionManager, port);

        assertThat(serverSocketFactory.port, equalTo(port));
    }

    @Test
    public void createsConnectionFromAcceptedSocket() throws IOException {
        Acceptor acceptor = new Acceptor(serverSocketFactory, connectionFactory, configuration, connectionManager, 1024);

        when(serverSocket.accept()).thenReturn(clientSocket);

        acceptor.execute();

        assertSame(connectionFactory.socket, clientSocket);
    }

    @Test
    public void createsConnectionFromAcceptedSocketUsingConfiguration() {
        Acceptor acceptor = new Acceptor(serverSocketFactory, connectionFactory, configuration, connectionManager, 1024);

        acceptor.execute();

        assertSame(connectionFactory.configuration, configuration);
    }

    @Test
    public void addsNewConnectionToConnectionManager() {
        Acceptor acceptor = new Acceptor(serverSocketFactory, connectionFactory, configuration, connectionManager, 1024);

        acceptor.execute();

        assertThat(connectionManager.getConnections().size(), equalTo(1));
        assertSame(connectionManager.getConnections().firstElement(), connection);
    }

    @Test
    public void executeHandlesIOException() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        Acceptor acceptor = new Acceptor(serverSocketFactory, connectionFactory, configuration, connectionManager, port);

        when(serverSocket.accept()).thenThrow(new IOException());

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Error accepting client connecting to port " + port);

        acceptor.execute();
    }

    private class MockServerSocketFactory extends ServerSocketFactory {
        private int port;
        private final ServerSocket serverSocket;

        private MockServerSocketFactory(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public ServerSocket createSocket(int port) {
            this.port = port;
            return serverSocket;
        }
    }

    private class MockConnectionFactory extends ConnectionFactory {
        private Socket socket;
        private Configuration configuration;

        @Override
        public Connection create(Socket socket, Configuration configuration) {
            this.socket = socket;
            this.configuration = configuration;
            return connection;
        }
    }
}