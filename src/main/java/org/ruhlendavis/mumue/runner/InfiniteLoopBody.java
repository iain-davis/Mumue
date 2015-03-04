package org.ruhlendavis.mumue.runner;

public interface InfiniteLoopBody {
    public boolean prepare();
    public boolean execute();
    public boolean cleanup();
}
