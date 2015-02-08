package org.ruhlendavis.meta.runner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.CleanCloser;

public class InfiniteLoopRunner extends CleanCloser implements Runnable {
    private final Configuration configuration;
    private final InfiniteLoopBody body;
    private boolean running = true;

    public InfiniteLoopRunner(Configuration configuration, InfiniteLoopBody body) {
        this.configuration = configuration;
        this.body = body;
    }

    @Override
    public void run() {
        body.prepare();
        while (running) {
            body.execute();
            if (configuration.isTest()) {
                break;
            }
        }
        body.cleanup();
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void stop() {
        running = false;
    }
}
