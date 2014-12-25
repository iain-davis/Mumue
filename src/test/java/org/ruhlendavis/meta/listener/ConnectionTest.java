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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.text.TextDao;
import org.ruhlendavis.meta.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    private final String text = RandomStringUtils.randomAlphabetic(257);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(5);
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Mock Socket socket;
    @Mock Configuration configuration;
    @Mock TextDao textDao;
    @InjectMocks Connection connection;

    @Before
    public void beforeEach() throws IOException {
        when(socket.getOutputStream()).thenReturn(output);
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textDao.getText(serverLocale, TextName.Welcome)).thenReturn(text);
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
