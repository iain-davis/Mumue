package org.ruhlendavis.meta.connection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InputReceiverTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private final Socket socket = mock(Socket.class);
    private final Collection<String> inputQueue = new ConcurrentLinkedQueue<>();
    private final InputReceiver inputReceiver = new InputReceiver(socket, inputQueue);

    @Test
    public void putReceivedLineOnInputQueue() throws IOException {
        String line = RandomStringUtils.randomAlphabetic(13);
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        when(socket.getInputStream()).thenReturn(input);
        inputReceiver.prepare();
        inputReceiver.execute();
        inputReceiver.cleanup();

        assertThat(inputQueue, contains(line));
    }

    @Test
    public void executeHandlesException() throws IOException {
        //noinspection unchecked
        when(socket.getInputStream()).thenThrow(IOException.class);

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Exception while reading input from socket");

        inputReceiver.execute();
    }
}
