package org.mumue.mumue.threading;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.mumue.mumue.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class InfiniteLoopRunnerTest {
    @Mock InfiniteLoopBody runnable;
    @Mock Configuration configuration;
    @InjectMocks InfiniteLoopRunner infiniteLoopRunner;

    @Before
    public void beforeEach() {
        when(runnable.prepare()).thenReturn(true);
        when(runnable.execute()).thenReturn(false);
    }

    @Test
    public void doNotRunForeverInTest() {
        infiniteLoopRunner.run();
    }

    @Test
    public void useRunnablePrepare() {
        infiniteLoopRunner.run();
        verify(runnable).prepare();
    }

    @Test
    public void executeGivenRunnable() {
        infiniteLoopRunner.run();
        verify(runnable).execute();
    }

    @Test
    public void doNotExecuteIfPrepareFails() {
        when(runnable.prepare()).thenReturn(false);
        infiniteLoopRunner.run();
        verify(runnable, never()).execute();
    }

    @Test
    public void setRunningFalseIfPrepareFails() {
        when(runnable.prepare()).thenReturn(false);
        infiniteLoopRunner.run();
        assertFalse(infiniteLoopRunner.isRunning());
    }

    @Test
    public void useRunnableCleanup() {
        infiniteLoopRunner.run();
        verify(runnable).cleanup();
    }

    @Test
    public void doNotCleanupIfPrepareFails() {
        when(runnable.prepare()).thenReturn(false);
        infiniteLoopRunner.run();
        verify(runnable, never()).cleanup();
    }

    @Test
    public void setRunningFalseWhenDone() {
        infiniteLoopRunner.run();
        assertFalse(infiniteLoopRunner.isRunning());
    }

    @Test
    public void doNotRunRunnableIfStopped() {
        infiniteLoopRunner.stop();
        infiniteLoopRunner.run();
        verify(runnable, never()).execute();
    }

    @Test
    public void isRunningReturnsTrue() {
        assertTrue(infiniteLoopRunner.isRunning());
    }

    @Test
    public void isRunningReturnsFalse() {
        infiniteLoopRunner.stop();
        assertFalse(infiniteLoopRunner.isRunning());
    }
}
