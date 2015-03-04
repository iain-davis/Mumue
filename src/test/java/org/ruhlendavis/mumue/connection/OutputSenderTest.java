package org.ruhlendavis.mumue.connection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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

import org.ruhlendavis.mumue.text.TextQueue;
import org.ruhlendavis.mumue.text.transformer.TransformerEngine;

@RunWith(MockitoJUnitRunner.class)
public class OutputSenderTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    private final String text = RandomStringUtils.randomAlphabetic(17);
    private final Socket socket = mock(Socket.class);
    private final TextQueue outputQueue = new TextQueue();
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final TransformerEngine transformerEngine = mock(TransformerEngine.class);
    private final OutputSender outputSender = new OutputSender(socket, outputQueue, transformerEngine);

    @Before
    public void beforeEach() throws IOException {
        when(socket.getOutputStream()).thenReturn(output);
        when(transformerEngine.transform(anyString())).thenReturn(text);
        when(socket.isConnected()).thenReturn(true);
    }

    @Test
    public void prepareReturnsTrue() {
        assertTrue(outputSender.prepare());
    }

    @Test
    public void executeReturnsTrue() {
        outputQueue.push(RandomStringUtils.randomAlphabetic(17));

        assertTrue(outputSender.execute());
    }

    @Test
    public void executeReturnsFalseWhenDisconnected() {
        when(socket.isConnected()).thenReturn(false);
        outputQueue.push(RandomStringUtils.randomAlphabetic(17));

        assertFalse(outputSender.execute());
    }

    @Test
    public void executeRemovesTextFromQueue() {
        outputQueue.push(RandomStringUtils.randomAlphabetic(17));

        outputSender.execute();

        assertTrue(outputQueue.isEmpty());
    }

    @Test
    public void executeTransformsText() {
        outputQueue.push(text);

        outputSender.execute();

        verify(transformerEngine).transform(text);
    }

    @Test
    public void executeWritesTextToOutput() {
        outputQueue.push(text);

        outputSender.execute();

        assertThat(new String(output.toByteArray()), equalTo(text));
    }

    @Test
    public void doNothingWithEmptyQueueAndReturnTrue() {
        assertTrue(outputSender.execute());
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

    @Test
    public void cleanupReturnsTrue() {
        assertTrue(outputSender.cleanup());
    }
}
