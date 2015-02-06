package org.ruhlendavis.meta.runner;

public interface InfiniteLoopRunnerRunnable {
    public void prepare();
    public void execute();
    public void cleanup();
}
