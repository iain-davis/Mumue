package org.mumue.mumue.connection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.player.Player;

public class ConnectionTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock
    ApplicationConfiguration configuration;
    @InjectMocks Connection connection;

    @Test
    public void inputQueueNotNull() {
        assertNotNull(connection.getInputQueue());
    }

    @Test
    public void outputQueueNotNull() {
        assertNotNull(connection.getOutputQueue());
    }

    @Test
    public void characterNotNull() {
        assertNotNull(connection.getCharacter());
    }

    @Test
    public void optionMapEmptyByDefault() {
        assertNotNull(connection.getMenuOptionIds());

        assertThat(connection.getMenuOptionIds().size(), equalTo(0));
    }

    @Test
    public void getLocaleNeverReturnsNull() {
        when(configuration.getServerLocale()).thenReturn("");
        assertNotNull(connection.getLocale());
    }

    @Test
    public void getLocaleReturnsPlayerLocale() {
        connection.setPlayer(new Player());
        String expected = RandomStringUtils.randomAlphabetic(5);
        connection.getPlayer().setLocale(expected);

        String locale = connection.getLocale();

        assertThat(locale, equalTo(expected));
    }

    @Test
    public void getLocaleWithoutPlayerReturnsServerLocale() {
        connection.setPlayer(null);
        String expected = RandomStringUtils.randomAlphabetic(5);
        when(configuration.getServerLocale()).thenReturn(expected);

        String locale = connection.getLocale();

        assertThat(locale, equalTo(expected));
    }

    @Test
    public void getLocaleWhenPlayerDoesNotHaveALocaleReturnsServerLocale() {
        Player player = new Player();
        player.setLocale("");
        connection.setPlayer(player);
        String expected = RandomStringUtils.randomAlphabetic(5);
        when(configuration.getServerLocale()).thenReturn(expected);

        String locale = connection.getLocale();

        assertThat(locale, equalTo(expected));
    }
}
