package org.mumue.mumue.connection;

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
import org.mumue.mumue.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionFactoryTest {
    @Mock Configuration configuration;
    @Mock Socket socket;
    @Mock ConnectionInitializer connectionInitializer;
    @InjectMocks ConnectionFactory connectionFactory;

    @Test
    public void createReturnsConnection() {
        assertThat(connectionFactory.create(socket), instanceOf(Connection.class));
    }

    @Test
    public void createInitializesConnection() {
        connectionFactory.create(socket);

        verify(connectionInitializer).initialize(eq(socket), any(Connection.class));
    }
}
