package org.mumue.mumue.connection;

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
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.threading.InfiniteLoopRunnerStarter;

public class ConnectionInitializerTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Connection connection;
    @Mock Configuration configuration;
    @Mock Socket socket;
    @Mock InfiniteLoopRunnerStarter infiniteLoopRunnerStarter;
    @Mock Injector injector;
    @Mock ConnectionController controller;
    @InjectMocks ConnectionInitializer connectionInitializer;

    @Before
    public void beforeEach() {
        when(injector.getInstance(ConnectionController.class)).thenReturn(controller);
    }

    @Test
    public void startsLoopRunnerForInputReceiver() {
        connectionInitializer.initialize(socket, connection);
        verify(infiniteLoopRunnerStarter).start(isA(InputReceiver.class));
    }

    @Test
    public void startsLoopRunnerForOutputSender() {
        connectionInitializer.initialize(socket, connection);
        verify(infiniteLoopRunnerStarter).start(isA(OutputSender.class));
    }

    @Test
    public void startsLoopRunnerForConnectionController() {
        connectionInitializer.initialize(socket, connection);
        verify(infiniteLoopRunnerStarter).start(isA(ConnectionController.class));
    }
}
