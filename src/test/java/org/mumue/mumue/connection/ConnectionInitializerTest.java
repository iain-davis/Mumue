package org.mumue.mumue.connection;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.threading.InfiniteLoopRunnerStarter;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionInitializerTest {
    @Mock Connection connection;
    @Mock Configuration configuration;
    @Mock Socket socket;
    @Mock InfiniteLoopRunnerStarter infiniteLoopRunnerStarter;
    @InjectMocks ConnectionInitializer connectionInitializer;

    @Test
    public void startsLoopRunnerForInputReceiver() {
        connectionInitializer.initialize(socket, connection, configuration);
        verify(infiniteLoopRunnerStarter).start(isA(InputReceiver.class));
    }

    @Test
    public void startsLoopRunnerForOutputSender() {
        connectionInitializer.initialize(socket, connection, configuration);
        verify(infiniteLoopRunnerStarter).start(isA(OutputSender.class));
    }

    @Test
    public void startsLoopRunnerForConnectionController() {
        connectionInitializer.initialize(socket, connection, configuration);
        verify(infiniteLoopRunnerStarter).start(isA(ConnectionController.class));
    }
}
