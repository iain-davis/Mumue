package org.ruhlendavis.mumue.threading;

public class InfiniteLoopRunnerStarter {
    private ThreadFactory threadFactory = new ThreadFactory();
    private InfiniteLoopRunnerFactory infiniteLoopRunnerFactory = new InfiniteLoopRunnerFactory();

    public void start(InfiniteLoopBody infiniteLoopBody) {
        InfiniteLoopRunner infiniteLoopRunner = infiniteLoopRunnerFactory.create(infiniteLoopBody);
        Thread thread = threadFactory.create(infiniteLoopRunner);
        thread.start();
    }
}
