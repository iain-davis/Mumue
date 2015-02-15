package org.ruhlendavis.meta.connection;


import static org.mockito.Matchers.any;
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
    @Mock Thread thread;

    @Mock InfiniteLoopRunnerStarter infiniteLoopRunnerStarter;

    @InjectMocks Connection connection;

    @Test
    public void startsLoopRunnerForInputReceiver() {
        connection.initialize(socket);
        verify(infiniteLoopRunnerStarter).start(any(ConnectionInputReceiver.class));
    }
}
