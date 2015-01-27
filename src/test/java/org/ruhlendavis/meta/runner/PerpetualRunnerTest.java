package org.ruhlendavis.meta.runner;

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

import org.ruhlendavis.meta.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class PerpetualRunnerTest {
    @Mock PerpetualRunnable runnable;
    @Mock Configuration configuration;
    @InjectMocks PerpetualRunner perpetualRunner;

    @Before
    public void beforeEach() {
        when(configuration.isTest()).thenReturn(true);
    }

    @Test
    public void doNotRunForeverInTest() {
        perpetualRunner.run();
    }

    @Test
    public void useRunnablePrepare() {
        perpetualRunner.run();
        verify(runnable).prepare();
    }

    @Test
    public void useRunnableCleanup() {
        perpetualRunner.run();
        verify(runnable).cleanup();
    }

    @Test
    public void runGivenRunnable() {
        perpetualRunner.run();
        verify(runnable).run();
    }

    @Test
    public void doNotRunRunnableIfStopped() {
        perpetualRunner.stop();
        perpetualRunner.run();
        verify(runnable, never()).run();
    }

    @Test
    public void isRunningReturnsTrue() {
        assertTrue(perpetualRunner.isRunning());
    }

    @Test
    public void isRunningReturnsFalse() {
        perpetualRunner.stop();
        assertFalse(perpetualRunner.isRunning());
    }
}
