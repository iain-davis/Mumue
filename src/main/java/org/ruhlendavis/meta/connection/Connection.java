package org.ruhlendavis.meta.connection;

import java.util.Vector;

public abstract class Connection extends CleanCloser implements Runnable {
    private Vector<String> linesReceived = new Vector<>();

    public Vector<String> getLinesReceived() {
        return linesReceived;
    }
}
