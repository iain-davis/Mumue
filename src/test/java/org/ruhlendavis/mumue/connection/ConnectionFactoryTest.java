package org.ruhlendavis.mumue.connection;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionFactoryTest {
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    @Mock Configuration configuration;

    @Test
    public void createReturnsConnection() {
        assertThat(connectionFactory.create(null, configuration), instanceOf(Connection.class));
    }
}
