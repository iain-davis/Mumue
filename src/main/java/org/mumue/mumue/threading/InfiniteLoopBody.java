package org.mumue.mumue.threading;

public interface InfiniteLoopBody {
    public boolean prepare();
    public boolean execute();
    public boolean cleanup();
}
