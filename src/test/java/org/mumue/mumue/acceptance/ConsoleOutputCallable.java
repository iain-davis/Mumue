package org.mumue.mumue.acceptance;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Callable;

class ConsoleOutputCallable implements Callable<String> {
    private final ByteArrayOutputStream output;

    public ConsoleOutputCallable(ByteArrayOutputStream output) {
        this.output = output;
    }

    @Override
    public String call() throws Exception {
        return output.toString();
    }
}
