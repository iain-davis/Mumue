package org.ruhlendavis.mumue.connection;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionFactoryTest {
    @Mock Configuration configuration;
    @Mock Socket socket;
    @Mock ConnectionInitializer connectionInitializer;
    @InjectMocks ConnectionFactory connectionFactory;

    @Test
    public void createReturnsConnection() {
        assertThat(connectionFactory.create(socket, configuration), instanceOf(Connection.class));
    }

    @Test
    public void createInitializesConnection() {
        connectionFactory.create(socket, configuration);

        verify(connectionInitializer).initialize(eq(socket), any(Connection.class), eq(configuration));
    }
}
