package org.ruhlendavis.meta.runner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.CleanCloser;

public class PerpetualRunner extends CleanCloser implements Runnable {
    private final Configuration configuration;
    private final PerpetualRunnable runnable;
    private boolean running = true;

    public PerpetualRunner(Configuration configuration, PerpetualRunnable runnable) {
        this.configuration = configuration;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        runnable.prepare();
        while (running) {
            runnable.run();
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
