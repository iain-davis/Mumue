package org.ruhlendavis.meta.runner;

public interface PerpetualRunnable extends Runnable {
    public void prepare();
    public void cleanup();
}
