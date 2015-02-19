package org.ruhlendavis.mumue.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.ruhlendavis.mumue.runner.InfiniteLoopBody;

public class InputReceiver implements InfiniteLoopBody {
    private final TextQueue inputQueue;
    private final Socket socket;

    public InputReceiver(Socket socket, TextQueue inputQueue) {
        this.socket = socket;
        this.inputQueue = inputQueue;
    }

    @Override
    public void prepare() {
    }

    @Override
    public void execute() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputQueue.push(input.readLine());
        } catch (IOException exception) {
            throw new RuntimeException("Exception while reading input from socket", exception);
        }
    }

    @Override
    public void cleanup() {
    }
}
