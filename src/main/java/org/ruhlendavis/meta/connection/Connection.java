package org.ruhlendavis.meta.connection;

import java.util.Vector;

import org.ruhlendavis.meta.configuration.Configuration;

public class Connection extends CleanCloser implements Runnable {
    private Configuration configuration;
    private Vector<String> linesReceived = new Vector<>();

    public Vector<String> getLinesReceived() {
        return linesReceived;
    }

    @Override
    public void run() {
        do {
//            for (String line : linesReceived) {
//
//            }
        } while (!configuration.isTest());
    }

    public Connection withConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }
}
