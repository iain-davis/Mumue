package org.ruhlendavis.meta.connection;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.ThreadFactory;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    @Mock Socket socket;
    @Mock Thread thread;

    @Mock ThreadFactory threadFactory;

    @InjectMocks Connection connection;

    @Before
    public void beforeEach() {
        when(threadFactory.create(any(Runnable.class))).thenReturn(thread);
    }

    @Test
    public void createsThreadForInputReceiver() {
        connection.initialize(socket);
        verify(threadFactory).create(any(Runnable.class));
    }

    @Test
    public void startsThreadForInputReceiver() {
        connection.initialize(socket);
        verify(thread).start();
    }
}
