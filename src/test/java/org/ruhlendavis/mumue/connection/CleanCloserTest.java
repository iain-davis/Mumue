package org.ruhlendavis.mumue.connection;

import static org.junit.Assert.assertTrue;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CleanCloserTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

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

    @Test
    public void closerHandlesException() {
        thrown.expect(RuntimeException.class);

        Closeable closeable = () -> {
            throw new IOException();
        };
        closer.close(closeable);
    }
}
