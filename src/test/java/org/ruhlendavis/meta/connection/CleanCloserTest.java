package org.ruhlendavis.meta.connection;

import static org.junit.Assert.assertTrue;

import java.io.Closeable;
import java.net.Socket;

import org.junit.Test;

public class CleanCloserTest {
    CleanCloser closer = new CleanCloser() {
        @Override
        protected void close(Closeable closeable) {
            super.close(closeable);
        }
    };

    @Test
    public void closerCloses() {
        Socket closeable = new Socket();
        closer.close(closeable);
        assertTrue(closeable.isClosed());
    }
}
