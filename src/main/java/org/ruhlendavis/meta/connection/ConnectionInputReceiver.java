package org.ruhlendavis.meta.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Collection;

import org.ruhlendavis.meta.runner.InfiniteLoopRunnerRunnable;

public class ConnectionInputReceiver implements InfiniteLoopRunnerRunnable {
    private final Collection<String> inputQueue;
    private final Socket socket;

    public ConnectionInputReceiver(Socket socket, Collection<String> inputQueue) {
        this.socket = socket;
        this.inputQueue = inputQueue;
    }

    @Override
    public void prepare() {}

    @Override
    public void execute() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputQueue.add(input.readLine());
        } catch (IOException exception) {
            throw new RuntimeException("Exception while reading input from socket", exception);
        }
    }

    @Override
    public void cleanup() {}
}
