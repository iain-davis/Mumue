package org.ruhlendavis.mumue.threading;

public class InfiniteLoopRunnerFactory {
    public InfiniteLoopRunner create(InfiniteLoopBody infiniteLoopBody) {
        return new InfiniteLoopRunner(infiniteLoopBody);
    }
}
