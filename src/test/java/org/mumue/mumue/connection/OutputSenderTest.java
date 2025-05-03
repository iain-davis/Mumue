package org.mumue.mumue.connection;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.text.TextQueue;
import org.mumue.mumue.text.transformer.TransformerEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OutputSenderTest {
    private final String text = RandomStringUtils.insecure().nextAlphabetic(17);
    private final Socket socket = mock(Socket.class);
    private final TextQueue outputQueue = new TextQueue();
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final TransformerEngine transformerEngine = mock(TransformerEngine.class);
    private final OutputSender outputSender = new OutputSender(socket, outputQueue, transformerEngine);

    @BeforeEach
    void beforeEach() throws IOException {
        when(socket.getOutputStream()).thenReturn(output);
        when(transformerEngine.transform(anyString())).thenReturn(text);
        when(socket.isConnected()).thenReturn(true);
    }

    @Test
    void prepareReturnsTrue() {
        boolean prepare = outputSender.prepare();
        assertThat(prepare, equalTo(true));
    }

    @Test
    void executeReturnsTrue() {
        outputQueue.push(RandomStringUtils.insecure().nextAlphabetic(17));

        boolean execute = outputSender.execute();

        assertThat(execute, equalTo(true));
    }

    @Test
    void executeReturnsFalseWhenDisconnected() {
        when(socket.isConnected()).thenReturn(false);
        outputQueue.push(RandomStringUtils.insecure().nextAlphabetic(17));

        boolean execute = outputSender.execute();

        assertThat(execute, equalTo(false));
    }

    @Test
    void executeRemovesTextFromQueue() {
        outputQueue.push(RandomStringUtils.insecure().nextAlphabetic(17));

        outputSender.execute();

        assertThat(outputQueue.isEmpty(), equalTo(true));
    }

    @Test
    void executeTransformsText() {
        outputQueue.push(text);

        outputSender.execute();

        verify(transformerEngine).transform(text);
    }

    @Test
    void executeWritesTextToOutput() {
        outputQueue.push(text);

        outputSender.execute();

        assertThat(output.toString(), equalTo(text));
    }

    @Test
    void doNothingWithEmptyQueueAndReturnTrue() {
        boolean execute = outputSender.execute();

        assertThat(execute, equalTo(true));
        assertThat(output.toString(), equalTo(""));
    }

    @Test
    void executeHandlesException() throws IOException {
        outputQueue.push(RandomStringUtils.insecure().nextAlphabetic(14));

        when(socket.getOutputStream()).thenThrow(new IOException());

        Exception exception = assertThrows(RuntimeException.class, outputSender::execute);

        assertThat(exception.getMessage(), equalTo("Exception when accessing output stream for client socket"));
    }

    @Test
    void cleanupReturnsTrue() {
        boolean cleanup = outputSender.cleanup();

        assertThat(cleanup, equalTo(true));
    }
}
