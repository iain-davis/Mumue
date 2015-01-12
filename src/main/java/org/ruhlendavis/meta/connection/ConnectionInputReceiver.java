package org.ruhlendavis.meta.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.ruhlendavis.meta.configuration.Configuration;

public class ConnectionInputReceiver extends CleanCloser implements Runnable {
    private Configuration configuration;
    private Socket socket;
    private Connection connection;

    @Override
    public void run() {
        do {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = input.readLine();
                connection.getLinesReceived().add(line);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        } while (!configuration.isTest());
    }

    public ConnectionInputReceiver withConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public ConnectionInputReceiver withSocket(Socket socket) {
        this.socket = socket;
        return this;
    }

    public ConnectionInputReceiver withConnection(Connection connection) {
        this.connection = connection;
        return this;
    }
}
