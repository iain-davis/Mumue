package org.mumue.mumue.threading;

public class InfiniteLoopRunnerFactory {
    public InfiniteLoopRunner create(InfiniteLoopBody infiniteLoopBody) {
        return new InfiniteLoopRunner(infiniteLoopBody);
    }
}
