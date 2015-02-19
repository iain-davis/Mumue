package org.ruhlendavis.mumue.connection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OutputSenderTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    private final Socket socket = mock(Socket.class);
    private final TextQueue outputQueue = new TextQueue();
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final OutputSender outputSender = new OutputSender(socket, outputQueue);

    @Before
    public void beforeEach() throws IOException {
        when(socket.getOutputStream()).thenReturn(output);
    }

    @Test
    public void executeRemovesLineFromQueue() {
        outputQueue.push(RandomStringUtils.randomAlphabetic(17));

        outputSender.prepare();
        outputSender.execute();
        outputSender.cleanup();

        assertTrue(outputQueue.isEmpty());
    }

    @Test
    public void executeWritesLineToOutput() {
        String line = RandomStringUtils.randomAlphabetic(17);
        outputQueue.push(line);

        outputSender.execute();

        assertThat(new String(output.toByteArray()), equalTo(line));
    }

    @Test
    public void doNothingWithEmptyQueue() {
        outputSender.execute();

        assertThat(new String(output.toByteArray()), equalTo(""));
    }

    @Test
    public void executeHandlesException() throws IOException {
        outputQueue.push(RandomStringUtils.randomAlphabetic(14));

        when(socket.getOutputStream()).thenThrow(new IOException());

        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Exception when accessing output stream for client socket");

        outputSender.execute();
    }
}
