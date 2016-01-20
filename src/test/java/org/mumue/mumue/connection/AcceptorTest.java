package org.mumue.mumue.connection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.PortConfiguration;
import org.mumue.mumue.importer.GlobalConstants;

public class AcceptorTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private static final Random RANDOM = new Random();
    private static final PrintStream ORIGINAL_CONSOLE_OUTPUT = System.out;
    private static final ByteArrayOutputStream CONSOLE_OUTPUT = new ByteArrayOutputStream();

    private final ServerSocket serverSocket = mock(ServerSocket.class);
    private final ServerSocketFactory serverSocketFactory = mock(ServerSocketFactory.class);
    private final ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    private final Socket clientSocket = mock(Socket.class);
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final Connection connection = new Connection(configuration);

    @BeforeClass
    public static void beforeClass() {
        System.setOut(new PrintStream(CONSOLE_OUTPUT));
    }

    @AfterClass
    public static void afterClass() {
        System.setOut(ORIGINAL_CONSOLE_OUTPUT);
    }

    @Before
    public void beforeEach() {
        connectionManager.getConnections().clear();
    }

    @Test
    public void displayMessageToConsole() {
        CONSOLE_OUTPUT.reset();
        PortConfiguration portConfiguration = new PortConfiguration();
        portConfiguration.setPort(RANDOM.nextInt(65534) + 1);
        Acceptor acceptor = new Acceptor(connectionFactory, connectionManager, portConfiguration, serverSocketFactory);

        acceptor.prepare();

        assertThat(CONSOLE_OUTPUT.toString(), startsWith(GlobalConstants.TELNET_LISTENING + portConfiguration.getPort()));
    }

    @Test
    public void usesSpecifiedPort() {
        PortConfiguration portConfiguration = new PortConfiguration();
        portConfiguration.setPort(RANDOM.nextInt(65534) + 1);
        Acceptor acceptor = new Acceptor(connectionFactory, connectionManager, portConfiguration, serverSocketFactory);

        acceptor.prepare();

        verify(serverSocketFactory).createSocket(portConfiguration.getPort());
    }

    @Test
    public void createsConnectionFromAcceptedSocket() throws IOException {
        PortConfiguration portConfiguration = new PortConfiguration();
        portConfiguration.setPort(RANDOM.nextInt(65534) + 1);
        Acceptor acceptor = new Acceptor(connectionFactory, connectionManager, portConfiguration, serverSocketFactory);

        when(serverSocketFactory.createSocket(portConfiguration.getPort())).thenReturn(serverSocket);
        when(serverSocket.accept()).thenReturn(clientSocket);

        acceptor.prepare();
        acceptor.execute();

        verify(connectionFactory).create(clientSocket, portConfiguration);
    }

    @Test
    public void addsNewConnectionToConnectionManager() throws IOException {
        PortConfiguration portConfiguration = new PortConfiguration();
        portConfiguration.setPort(RANDOM.nextInt(65534) + 1);
        Acceptor acceptor = new Acceptor(connectionFactory, connectionManager, portConfiguration, serverSocketFactory);

        when(serverSocketFactory.createSocket(portConfiguration.getPort())).thenReturn(serverSocket);
        when(serverSocket.accept()).thenReturn(clientSocket);
        when(connectionFactory.create(clientSocket, portConfiguration)).thenReturn(connection);

        acceptor.prepare();
        acceptor.execute();

        assertThat(connectionManager.getConnections().size(), equalTo(1));
        assertSame(connectionManager.getConnections().firstElement(), connection);
    }

    @Test
    public void executeHandlesIOException() throws IOException {
        int port = RandomUtils.nextInt(2048, 4096);
        PortConfiguration portConfiguration = new PortConfiguration();
        portConfiguration.setPort(port);
        Acceptor acceptor = new Acceptor(connectionFactory, connectionManager, portConfiguration, serverSocketFactory);

        when(serverSocketFactory.createSocket(port)).thenReturn(serverSocket);
        when(serverSocket.accept()).thenThrow(new IOException());

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Error accepting client connecting to port " + port);

        acceptor.prepare();
        acceptor.execute();
    }
}