package org.ruhlendavis.mumue.runner;

public interface InfiniteLoopBody {
    public void prepare();
    public void execute();
    public void cleanup();
}
