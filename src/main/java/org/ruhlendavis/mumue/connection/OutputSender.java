package org.ruhlendavis.mumue.connection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.ruhlendavis.mumue.threading.InfiniteLoopBody;
import org.ruhlendavis.mumue.text.TextQueue;
import org.ruhlendavis.mumue.text.transformer.TransformerEngine;

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
    public boolean prepare() {
        return true;
    }

    @Override
    public boolean execute() {
        if (!socket.isConnected()){
            return false;
        }
        if (outputQueue.isEmpty()) {
            return true;
        }
        try {
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String text = transformerEngine.transform(outputQueue.pop());
            output.write(text);
            output.flush();
            return true;
        } catch (IOException exception) {
            throw new RuntimeException("Exception when accessing output stream for client socket", exception);
        }
    }

    @Override
    public boolean cleanup() {
        return true;
    }
}
