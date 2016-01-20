package org.mumue.mumue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.junit.Test;
import org.mumue.mumue.connection.Acceptor;
import org.mumue.mumue.threading.InfiniteLoopRunner;

public class AcceptorStarterTest {
    private final ExecutorService executorService = mock(ExecutorService.class);
    private final AcceptorStarter starter = new AcceptorStarter(executorService);
    
    @Test
    public void neverReturnNull() {
        assertThat(starter.start(new ArrayList<>()), notNullValue());
    }

    @Test
    public void submitForEachAcceptor() {
        List<Acceptor> acceptors = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            acceptors.add(mock(Acceptor.class));
        }

        Collection<Future<?>> tasks = starter.start(acceptors);
        assertThat(tasks.size(), equalTo(4));

        verify(executorService, times(4)).submit((InfiniteLoopRunner)anyObject());
    }
}