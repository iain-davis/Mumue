package org.ruhlendavis.meta.connection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class AcceptorBuilderTest {
    @Mock Configuration configuration;
    private final AcceptorBuilder acceptorBuilder = new AcceptorBuilder();

    @Test
    public void buildConnectionAcceptor() {
        Acceptor acceptor = acceptorBuilder.build(0, null, configuration);

        assertThat(acceptor, instanceOf(Acceptor.class));
    }

    @Test
    public void buildConnectionAcceptorWithGivenPort() {
        int port = RandomUtils.nextInt(1024, 2048);

        Acceptor acceptor = acceptorBuilder.build(port, null, configuration);

        assertThat(port, equalTo(acceptor.getPort()));
    }

    @Test
    public void buildConnectionAcceptorWithGiveConnectionManager() {
        ConnectionManager connectionManager = new ConnectionManager();

        Acceptor acceptor = acceptorBuilder.build(0, connectionManager, configuration);

        assertThat(connectionManager, sameInstance(acceptor.getConnectionManager()));
    }
}
