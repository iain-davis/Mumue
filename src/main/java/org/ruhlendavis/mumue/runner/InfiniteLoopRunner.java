package org.ruhlendavis.mumue.runner;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.CleanCloser;

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
        if(!body.prepare()) {
            stop();
            return;
        }

        while (running) {
            if (!body.execute()) {
                stop();
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
