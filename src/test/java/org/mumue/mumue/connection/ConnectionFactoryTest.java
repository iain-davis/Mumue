package org.mumue.mumue.connection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.Socket;

import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.PortConfiguration;
import org.mumue.mumue.threading.InfiniteLoopRunnerStarter;

public class ConnectionFactoryTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    private final PortConfiguration portConfiguration = new PortConfiguration();
    @Mock Connection connection;
    @Mock ApplicationConfiguration configuration;
    @Mock Socket socket;
    @Mock InfiniteLoopRunnerStarter infiniteLoopRunnerStarter;
    @Mock Injector injector;
    @Mock ConnectionController controller;
    @InjectMocks ConnectionFactory connectionFactory;

    @Before
    public void beforeEach() {
        when(injector.getInstance(ConnectionController.class)).thenReturn(controller);
    }

    @Test
    public void neverReturnNull() {
        assertThat(connectionFactory.create(socket, portConfiguration), notNullValue());
    }

    @Test
    public void setPortConfiguration() {
        assertSame(connectionFactory.create(socket, portConfiguration).getPortConfiguration(), portConfiguration);
    }

    @Test
    public void startsLoopRunnerForInputReceiver() {
        connectionFactory.create(socket, portConfiguration);
        verify(infiniteLoopRunnerStarter).start(isA(InputReceiver.class));
    }

    @Test
    public void startsLoopRunnerForOutputSender() {
        connectionFactory.create(socket, portConfiguration);
        verify(infiniteLoopRunnerStarter).start(isA(OutputSender.class));
    }

    @Test
    public void startsLoopRunnerForConnectionController() {
        connectionFactory.create(socket, portConfiguration);
        verify(infiniteLoopRunnerStarter).start(isA(ConnectionController.class));
    }
}
