package org.mumue.mumue.threading;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InfiniteLoopRunnerFactoryTest {
    private final InfiniteLoopRunnerFactory infiniteLoopRunnerFactory = new InfiniteLoopRunnerFactory();
    @Mock InfiniteLoopBody infiniteLoopBody;

    @Test
    public void createReturnsInfiniteLoopRunner() {
        assertThat(infiniteLoopRunnerFactory.create(infiniteLoopBody), instanceOf(InfiniteLoopRunner.class));
    }
}
