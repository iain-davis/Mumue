package org.ruhlendavis.meta.listener;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    private final String text = RandomStringUtils.randomAlphabetic(257);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(5);

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    @Mock Socket socket;
    @Mock Configuration configuration;
    private Connection connection;

    @Before
    public void beforeEach() throws IOException {
        when(socket.getOutputStream()).thenReturn(output);
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(configuration.getText(serverLocale, TextName.Welcome)).thenReturn(text);
        connection = new Connection().withSocket(socket).withConfiguration(configuration);
    }

    @Test
    public void connectionUsesProvidedSocket() {
        connection.run();
        assertTrue(StringUtils.isNotBlank(output.toString()));
    }

    @Test
    public void sendWelcomeToSocket() {
        connection.run();
        assertThat(output.toString(), equalTo(text));
    }
}
