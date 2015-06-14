package org.mumue.mumue.connection;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public abstract class CleanCloser {
    protected void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        } finally {
            IOUtils.closeQuietly(closeable);
        }
    }
}
