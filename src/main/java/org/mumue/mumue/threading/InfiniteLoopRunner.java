package org.mumue.mumue.threading;

import org.mumue.mumue.connection.CleanCloser;

public class InfiniteLoopRunner extends CleanCloser implements Runnable {
    private final InfiniteLoopBody body;
    private boolean running = true;

    public InfiniteLoopRunner(InfiniteLoopBody body) {
        this.body = body;
    }

    @Override
    public void run() {
        if (!body.prepare()) {
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