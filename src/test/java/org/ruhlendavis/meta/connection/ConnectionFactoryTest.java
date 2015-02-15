package org.ruhlendavis.meta.connection;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ConnectionFactoryTest {
    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    @Test
    public void createReturnsConnection() {
        assertThat(connectionFactory.create(null), instanceOf(Connection.class));
    }
}
