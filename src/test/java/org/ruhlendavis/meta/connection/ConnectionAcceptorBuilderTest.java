package org.ruhlendavis.meta.connection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionAcceptorBuilderTest {
    private final ConnectionAcceptorBuilder connectionAcceptorBuilder = new ConnectionAcceptorBuilder();

    @Test
    public void buildConnectionAcceptor() {
        ConnectionAcceptor connectionAcceptor = connectionAcceptorBuilder.build(0, null);

        assertThat(connectionAcceptor, instanceOf(ConnectionAcceptor.class));
    }

    @Test
    public void buildConnectionAcceptorWithGivenPort() {
        int port = RandomUtils.nextInt(1024, 2048);

        ConnectionAcceptor connectionAcceptor = connectionAcceptorBuilder.build(port, null);

        assertThat(port, equalTo(connectionAcceptor.getPort()));
    }

    @Test
    public void buildConnectionAcceptorWithGiveConnectionManager() {
        ConnectionManager connectionManager = new ConnectionManager();

        ConnectionAcceptor connectionAcceptor = connectionAcceptorBuilder.build(0, connectionManager);

        assertThat(connectionManager, sameInstance(connectionAcceptor.getConnectionManager()));
    }
}
