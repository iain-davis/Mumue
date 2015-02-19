package org.ruhlendavis.mumue.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.ruhlendavis.mumue.runner.InfiniteLoopBody;

public class OutputSender implements InfiniteLoopBody {
    private final Socket socket;
    private final TextQueue outputQueue;

    public OutputSender(Socket socket, TextQueue outputQueue) {
        this.socket = socket;
        this.outputQueue = outputQueue;
    }

    @Override
    public void prepare() {

    }

    @Override
    public void execute() {
        if (outputQueue.isEmpty()) {
            return;
        }
        try {
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String text = outputQueue.pop();
            output.write(text);
            output.flush();
        } catch (IOException exception) {
            throw new RuntimeException("Exception when accessing output stream for client socket", exception);
        }
    }

    @Override
    public void cleanup() {

    }
}
