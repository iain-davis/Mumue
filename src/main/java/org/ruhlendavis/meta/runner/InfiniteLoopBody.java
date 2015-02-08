package org.ruhlendavis.meta.runner;

public interface InfiniteLoopBody {
    public void prepare();
    public void execute();
    public void cleanup();
}
