package org.ruhlendavis.meta.connection;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.stages.ConnectionStage;
import org.ruhlendavis.meta.runner.InfiniteLoopRunnerStarter;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    @Mock Configuration configuration;
    @Mock Socket socket;
    @Mock InfiniteLoopRunnerStarter infiniteLoopRunnerStarter;
    @InjectMocks Connection connection;

    @Test
    public void startsLoopRunnerForInputReceiver() {
        connection.initialize(socket, configuration);
        verify(infiniteLoopRunnerStarter).start(isA(InputReceiver.class));
    }

    @Test
    public void startsLoopRunnerForOutputSender() {
        connection.initialize(socket, configuration);
        verify(infiniteLoopRunnerStarter).start(isA(OutputSender.class));
    }

    @Test
    public void startsLoopRunnerForConnectionController() {
        connection.initialize(socket, configuration);
        verify(infiniteLoopRunnerStarter).start(isA(ConnectionController.class));
    }
}
