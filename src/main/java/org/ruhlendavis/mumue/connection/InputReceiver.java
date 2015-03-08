package org.ruhlendavis.mumue.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.ruhlendavis.mumue.runner.InfiniteLoopBody;
import org.ruhlendavis.mumue.text.TextQueue;

public class InputReceiver implements InfiniteLoopBody {
    private final TextQueue inputQueue;
    private final Socket socket;

    public InputReceiver(Socket socket, TextQueue inputQueue) {
        this.socket = socket;
        this.inputQueue = inputQueue;
    }

    @Override
    public boolean prepare() {
        return true;
    }

    @Override
    public boolean execute() {
        if (!socket.isConnected()) {
            return false;
        }
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputQueue.push(input.readLine());
            return true;
        } catch (IOException exception) {
            throw new RuntimeException("Exception while reading input from socket", exception);
        }
    }

    @Override
    public boolean cleanup() {
        return true;
    }
}
