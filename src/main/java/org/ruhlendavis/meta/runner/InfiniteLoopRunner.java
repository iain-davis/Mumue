package org.ruhlendavis.meta.runner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.CleanCloser;

public class InfiniteLoopRunner extends CleanCloser implements Runnable {
    private final Configuration configuration;
    private final InfiniteLoopRunnerRunnable runnable;
    private boolean running = true;

    public InfiniteLoopRunner(Configuration configuration, InfiniteLoopRunnerRunnable runnable) {
        this.configuration = configuration;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        runnable.prepare();
        while (running) {
            runnable.execute();
            if (configuration.isTest()) {
                break;
            }
        }
        runnable.cleanup();
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void stop() {
        running = false;
    }
}
