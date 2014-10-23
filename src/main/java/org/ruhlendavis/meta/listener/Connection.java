package org.ruhlendavis.meta.listener;

import java.net.Socket;

public class Connection implements Runnable {
    @Override
    public void run() {
    }

    public Connection withSocket(Socket socket) {
        return this;
    }
}
