package org.ruhlendavis.meta.listener;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public abstract class CleanCloser {
    protected void close(Closeable socket) {
        try {
            socket.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        } finally {
            IOUtils.closeQuietly(socket);
        }
    }
}
