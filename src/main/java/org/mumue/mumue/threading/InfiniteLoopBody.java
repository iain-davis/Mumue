package org.mumue.mumue.threading;

public interface InfiniteLoopBody {
    boolean prepare();
    boolean execute();
    boolean cleanup();
}
