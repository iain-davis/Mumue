package org.mumue.mumue.connection;

import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.PortConfiguration;
import org.mumue.mumue.threading.InfiniteLoopRunnerStarter;

import java.net.Socket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConnectionFactoryTest {
    private final PortConfiguration portConfiguration = new PortConfiguration();
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final Socket socket = mock(Socket.class);
    private final InfiniteLoopRunnerStarter infiniteLoopRunnerStarter = mock(InfiniteLoopRunnerStarter.class);
    private final Injector injector = mock(Injector.class);
    private final ConnectionController controller = mock(ConnectionController.class);
    private final ConnectionFactory connectionFactory = new ConnectionFactory(configuration, injector, infiniteLoopRunnerStarter);

    @BeforeEach
    void beforeEach() {
        when(injector.getInstance(ConnectionController.class)).thenReturn(controller);
    }

    @Test
    void neverReturnNull() {
        assertThat(connectionFactory.create(socket, portConfiguration), notNullValue());
    }

    @Test
    void setPortConfiguration() {
        Connection connection = connectionFactory.create(socket, portConfiguration);

        assertThat(connection.getPortConfiguration(), sameInstance(portConfiguration));
    }

    @Test
    void startsLoopRunnerForInputReceiver() {
        connectionFactory.create(socket, portConfiguration);
        verify(infiniteLoopRunnerStarter).start(isA(InputReceiver.class));
    }

    @Test
    void startsLoopRunnerForOutputSender() {
        connectionFactory.create(socket, portConfiguration);
        verify(infiniteLoopRunnerStarter).start(isA(OutputSender.class));
    }

    @Test
    void startsLoopRunnerForConnectionController() {
        connectionFactory.create(socket, portConfiguration);
        verify(infiniteLoopRunnerStarter).start(isA(ConnectionController.class));
    }
}
