package org.mumue.mumue.connection;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.text.TextQueue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InputReceiverTest {
    private final Socket socket = mock(Socket.class);
    private final TextQueue inputQueue = new TextQueue();
    private final InputReceiver inputReceiver = new InputReceiver(socket, inputQueue);

    @Test
    void prepareReturnsTrue() {
        boolean prepare = inputReceiver.prepare();

        assertThat(prepare, equalTo(true));
    }

    @Test
    void putReceivedLineOnInputQueue() throws IOException {
        when(socket.isConnected()).thenReturn(true);
        String line = RandomStringUtils.insecure().nextAlphabetic(13);
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        when(socket.getInputStream()).thenReturn(input);
        inputReceiver.execute();

        assertThat(inputQueue, contains(line));
    }

    @Test
    void executeReturnsTrue() throws IOException {
        when(socket.isConnected()).thenReturn(true);
        String line = RandomStringUtils.insecure().nextAlphabetic(13);
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        when(socket.getInputStream()).thenReturn(input);

        assertTrue(inputReceiver.execute());
    }

    @Test
    void doNotGetInputStreamWhenSocketDisconnected() throws IOException {
        verify(socket, never()).getInputStream();
    }

    @Test
    void executeReturnsFalseWhenSocketDisconnected() {
        when(socket.isConnected()).thenReturn(false);

        assertFalse(inputReceiver.execute());
    }

    @Test
    void executeHandlesException() throws IOException {
        when(socket.isConnected()).thenReturn(true);
        //noinspection unchecked
        when(socket.getInputStream()).thenThrow(IOException.class);

        Exception exception = assertThrows(RuntimeException.class, inputReceiver::execute);
        assertThat(exception.getMessage(), equalTo("Exception while reading input from socket"));
    }

    @Test
    void cleanupReturnsTrue() {
        boolean cleanup = inputReceiver.cleanup();

        assertThat(cleanup, equalTo(true));
    }
}
