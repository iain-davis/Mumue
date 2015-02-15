package org.ruhlendavis.meta.runner;

import org.ruhlendavis.meta.ThreadFactory;

public class InfiniteLoopRunnerStarter {
    private ThreadFactory threadFactory = new ThreadFactory();
    private InfiniteLoopRunnerFactory infiniteLoopRunnerFactory = new InfiniteLoopRunnerFactory();

    public void start(InfiniteLoopBody infiniteLoopBody) {
        InfiniteLoopRunner infiniteLoopRunner = infiniteLoopRunnerFactory.create(infiniteLoopBody);
        Thread thread = threadFactory.create(infiniteLoopRunner);
        thread.start();
    }
}
