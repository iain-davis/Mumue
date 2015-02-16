package org.ruhlendavis.meta.connection;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.runner.InfiniteLoopRunnerStarter;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    @Mock Socket socket;

    @Mock InfiniteLoopRunnerStarter infiniteLoopRunnerStarter;

    @InjectMocks Connection connection;

    @Test
    public void startsLoopRunnerForInputReceiver() {
        connection.initialize(socket);
        verify(infiniteLoopRunnerStarter).start(isA(InputReceiver.class));
    }

    @Test
    public void startsLoopRunnerForOutputSender() {
        connection.initialize(socket);
        verify(infiniteLoopRunnerStarter).start(isA(OutputSender.class));
    }

    @Test
    public void startsLoopRunnerForInputProcessor() {
        connection.initialize(socket);
        verify(infiniteLoopRunnerStarter).start(isA(InputProcessor.class));
    }
}
