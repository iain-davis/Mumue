package org.mumue.mumue;

import org.junit.jupiter.api.Test;
import org.mumue.mumue.configuration.PortConfiguration;
import org.mumue.mumue.configuration.PortType;
import org.mumue.mumue.connection.Acceptor;
import org.mumue.mumue.connection.ConnectionFactory;
import org.mumue.mumue.connection.ConnectionManager;
import org.mumue.mumue.connection.ServerSocketFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;

class AcceptorsBuilderTest {
    private static final Random RANDOM = new Random();
    private final ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    private final ConnectionManager connectionManager = new ConnectionManager();
    private final ServerSocketFactory serverSocketFactory = mock(ServerSocketFactory.class);
    private final AcceptorsBuilder acceptorsBuilder = new AcceptorsBuilder(connectionFactory, connectionManager, serverSocketFactory);

    @Test
    void neverReturnNull() {
        assertThat(acceptorsBuilder.build(new ArrayList<>()), notNullValue());
    }

    @Test
    void makeAcceptorForEachPortConfiguration() {
        List<PortConfiguration> portConfigurations = new ArrayList<>();
        for (int i = 0; i < 4; i++) {

            portConfigurations.add(new PortConfiguration());
        }

        Collection<Acceptor> acceptors = acceptorsBuilder.build(portConfigurations);

        assertThat(acceptors.size(), equalTo(4));
    }

    @Test
    void setPortConfigurationOnEachAcceptor() {
        List<PortConfiguration> portConfigurations = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            PortConfiguration portConfiguration = new PortConfiguration();
            portConfiguration.setPort(RANDOM.nextInt(65534) + 1);
            portConfiguration.setSupportsMenus(RANDOM.nextBoolean());
            portConfiguration.setType(PortType.SSH);
            portConfigurations.add(portConfiguration);
        }
        Collection<Acceptor> acceptors = acceptorsBuilder.build(portConfigurations);
        for (Acceptor acceptor : acceptors) {
            assertThat(acceptor.getPortConfiguration(), in(portConfigurations));
        }
    }
}
