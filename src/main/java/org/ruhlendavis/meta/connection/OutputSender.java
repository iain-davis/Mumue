package org.ruhlendavis.meta.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Collection;

import org.ruhlendavis.meta.runner.InfiniteLoopBody;

public class OutputSender implements InfiniteLoopBody {
    private final Socket socket;
    private final Collection<String> outputQueue;

    public OutputSender(Socket socket, Collection<String> outputQueue) {
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
            String line = outputQueue.stream().findAny().get();
            output.write(line);
            output.flush();
            outputQueue.remove(line);
        } catch (IOException exception) {
            throw new RuntimeException("Exception when accessing output stream for client socket", exception);
        }
    }

    @Override
    public void cleanup() {

    }
}