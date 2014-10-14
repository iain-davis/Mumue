package org.ruhlendavis.meta.listener;

import java.net.Socket;

public class Connection implements Runnable {
    private Socket socket;

    @Override
    public void run() {
    }

    public Connection withSocket(Socket socket) {
        this.socket = socket;
        return this;
    }
}
