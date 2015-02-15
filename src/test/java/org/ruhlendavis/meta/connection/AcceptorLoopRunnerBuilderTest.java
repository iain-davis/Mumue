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
    @Mock ConnectionAcceptor connectionAcceptor;
    @Mock ConnectionManager connectionManager;
    @Mock Configuration configuration;

    @Mock ConnectionAcceptorBuilder connectionAcceptorBuilder;
    @InjectMocks AcceptorLoopRunnerBuilder acceptorLoopRunnerBuilder;

    @Test
    public void buildReturnsInfiniteLoopRunner() {
        assertThat(acceptorLoopRunnerBuilder.build(configuration, null), instanceOf(InfiniteLoopRunner.class));
    }

    @Test
    public void buildBuildsConnectionAcceptor() {
        when(configuration.getTelnetPort()).thenReturn(PORT);
        when(connectionAcceptorBuilder.build(PORT, connectionManager)).thenReturn(connectionAcceptor);
        acceptorLoopRunnerBuilder.build(configuration, connectionManager);
        verify(connectionAcceptorBuilder).build(PORT, connectionManager);
    }
}
