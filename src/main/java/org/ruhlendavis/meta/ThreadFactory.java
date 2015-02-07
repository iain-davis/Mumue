package org.ruhlendavis.meta;

public class ThreadFactory {
    public Thread createThread(Runnable runnable, String name) {
        return new Thread(runnable, name);
    }
}
