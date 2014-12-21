package org.ruhlendavis.meta.listener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.text.TextName;

public class Connection extends CleanCloser implements Runnable {
    private Configuration configuration;
    private Socket socket;

    @Override
    public void run() {
        String welcomeText = configuration.getText(configuration.getServerLocale(), TextName.Welcome);
        OutputStream output;
        try {
            output = socket.getOutputStream();
            output.write(welcomeText.getBytes());
        } catch (IOException exception) {
            close(socket);
            throw new RuntimeException(exception);
        }
    }

    public Connection withConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public Connection withSocket(Socket socket) {
        this.socket = socket;
        return this;
    }
}
