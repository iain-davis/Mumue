package org.ruhlendavis.meta.connection;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.ThreadFactory;
import org.ruhlendavis.meta.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    @Mock ThreadFactory threadFactory;
    @Mock Configuration configuration;
    @Mock Socket socket;
    @InjectMocks Connection connection;

    @Test
    public void startsInputReceiver() {
        connection.initialize(configuration, socket);
        verify(threadFactory).create(any());
    }
}
