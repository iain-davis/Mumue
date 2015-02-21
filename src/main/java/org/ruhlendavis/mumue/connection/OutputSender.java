package org.ruhlendavis.mumue.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.ruhlendavis.mumue.runner.InfiniteLoopBody;
import org.ruhlendavis.mumue.texttransformer.TransformerEngine;

public class OutputSender implements InfiniteLoopBody {
    private final Socket socket;
    private final TextQueue outputQueue;
    private final TransformerEngine transformerEngine;

    public OutputSender(Socket socket, TextQueue outputQueue, TransformerEngine transformerEngine) {
        this.socket = socket;
        this.outputQueue = outputQueue;
        this.transformerEngine = transformerEngine;
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
            String text = transformerEngine.transform(outputQueue.pop());
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
