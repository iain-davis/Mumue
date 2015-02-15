package org.ruhlendavis.meta.runner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.ThreadFactory;

@RunWith(MockitoJUnitRunner.class)
public class InfiniteLoopRunnerStarterTest {
    @Mock Thread thread;
    @Mock InfiniteLoopBody infiniteLoopBody;
    @Mock InfiniteLoopRunner infiniteLoopRunner;

    @Mock ThreadFactory threadFactory;
    @Mock InfiniteLoopRunnerFactory infiniteLoopRunnerFactory;
    @InjectMocks InfiniteLoopRunnerStarter infiniteLoopRunnerStarter;

    @Before
    public void beforeEach() {
        when(infiniteLoopRunnerFactory.create(infiniteLoopBody)).thenReturn(infiniteLoopRunner);
        when(threadFactory.create(any(Runnable.class))).thenReturn(thread);
    }

    @Test
    public void createsInfiniteLoopRunner() {
        infiniteLoopRunnerStarter.start(infiniteLoopBody);
        verify(infiniteLoopRunnerFactory).create(infiniteLoopBody);
    }

    @Test
    public void startsThread() {
        infiniteLoopRunnerStarter.start(infiniteLoopBody);
        verify(thread).start();
    }
}
