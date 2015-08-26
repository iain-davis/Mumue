package org.mumue.mumue.connection;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketFactory {
    public ServerSocket createSocket(int port) {
        try {
            return new ServerSocket(port);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
