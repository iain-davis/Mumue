package org.ruhlendavis.meta.listener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

public class Connection extends CleanCloser implements Runnable {
    private Configuration configuration;
    private Socket socket;
    private TextMaker textMaker = new TextMaker();

    @Override
    public void run() {
        String welcomeText = textMaker.getText(configuration.getServerLocale(), TextName.Welcome);
        String loginPrompt = textMaker.getText(configuration.getServerLocale(), TextName.LoginPrompt);

        OutputStream output;
        try {
            output = socket.getOutputStream();
            output.write(welcomeText.getBytes());
            output.write(loginPrompt.getBytes());
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
