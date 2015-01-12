package org.ruhlendavis.meta.connection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionInputReceiverTest {
    private Connection connection = new Connection() {
        @Override
        public void run() {

        }
    };

    @Mock Socket socket;
    @Mock Configuration configuration;
    @InjectMocks ConnectionInputReceiver connectionInputReceiver;

    @Before
    public void beforeEach() throws IOException {
        when(configuration.isTest()).thenReturn(true);
        connectionInputReceiver.withConnection(connection).withSocket(socket);
    }

    @Test
    public void doNotRunForeverInTest() throws IOException {
        String empty = "";
        ByteArrayInputStream input = new ByteArrayInputStream(empty.getBytes());
        when(socket.getInputStream()).thenReturn(input);
        connectionInputReceiver.run();
    }

    @Test
    public void putReceivedLineOnInputQueue() throws IOException {
        String line = RandomStringUtils.randomAlphabetic(13);
        ByteArrayInputStream input = new ByteArrayInputStream(line.getBytes());
        when(socket.getInputStream()).thenReturn(input);
        connectionInputReceiver.run();
        assertThat(connection.getLinesReceived().get(0), equalTo(line));
    }
}
