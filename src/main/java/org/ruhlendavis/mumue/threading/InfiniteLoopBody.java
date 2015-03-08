package org.ruhlendavis.mumue.threading;

public interface InfiniteLoopBody {
    public boolean prepare();
    public boolean execute();
    public boolean cleanup();
}
