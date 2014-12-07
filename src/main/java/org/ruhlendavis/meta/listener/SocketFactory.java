package org.ruhlendavis.meta.listener;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketFactory {
    public ServerSocket createSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
