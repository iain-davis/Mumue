package org.ruhlendavis.meta.connection;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.runner.InfiniteLoopRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcceptorLoopRunnerBuilderTest {
    private static final int PORT = RandomUtils.nextInt(1024, 2048);
    @Mock Acceptor acceptor;
    @Mock ConnectionManager connectionManager;
    @Mock Configuration configuration;

    @Mock AcceptorBuilder acceptorBuilder;
    @InjectMocks AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder;

    @Test
    public void buildReturnsInfiniteLoopRunner() {
        assertThat(acceptorLoopRunnerBuilder.build(configuration, null), instanceOf(InfiniteLoopRunner.class));
    }

    @Test
    public void buildBuildsConnectionAcceptor() {
        when(configuration.getTelnetPort()).thenReturn(PORT);
        when(acceptorBuilder.build(PORT, connectionManager)).thenReturn(acceptor);
        acceptorLoopRunnerBuilder.build(configuration, connectionManager);
        verify(acceptorBuilder).build(PORT, connectionManager);
    }
}
