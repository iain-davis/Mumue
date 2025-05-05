package org.mumue.mumue.threading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InfiniteLoopRunnerTest {
    private final InfiniteLoopBody runnable = mock(InfiniteLoopBody.class);
    private final InfiniteLoopRunner infiniteLoopRunner = new InfiniteLoopRunner(runnable);

    @BeforeEach
    void beforeEach() {
        when(runnable.prepare()).thenReturn(true);
        when(runnable.execute()).thenReturn(false);
    }

    @Test
    void doNotRunForeverInTest() {
        infiniteLoopRunner.run();
    }

    @Test
    void useRunnablePrepare() {
        infiniteLoopRunner.run();
        verify(runnable).prepare();
    }

    @Test
    void executeGivenRunnable() {
        infiniteLoopRunner.run();
        verify(runnable).execute();
    }

    @Test
    void doNotExecuteIfPrepareFails() {
        when(runnable.prepare()).thenReturn(false);
        infiniteLoopRunner.run();
        verify(runnable, never()).execute();
    }

    @Test
    void setRunningFalseIfPrepareFails() {
        when(runnable.prepare()).thenReturn(false);
        infiniteLoopRunner.run();
        assertThat(infiniteLoopRunner.isRunning(), equalTo(false));
    }

    @Test
    void useRunnableCleanup() {
        infiniteLoopRunner.run();
        verify(runnable).cleanup();
    }

    @Test
    void doNotCleanupIfPrepareFails() {
        when(runnable.prepare()).thenReturn(false);
        infiniteLoopRunner.run();
        verify(runnable, never()).cleanup();
    }

    @Test
    void setRunningFalseWhenDone() {
        infiniteLoopRunner.run();
        assertThat(infiniteLoopRunner.isRunning(), equalTo(false));
    }

    @Test
    void doNotRunRunnableIfStopped() {
        infiniteLoopRunner.stop();
        infiniteLoopRunner.run();
        verify(runnable, never()).execute();
    }

    @Test
    void isRunningReturnsTrue() {
        assertThat(infiniteLoopRunner.isRunning(), equalTo(true));
    }

    @Test
    void isRunningReturnsFalse() {
        infiniteLoopRunner.stop();
        assertThat(infiniteLoopRunner.isRunning(), equalTo(false));
    }
}
