package org.mumue.mumue.connection;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.text.TextQueue;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConnectionTest {
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final Connection connection = new Connection(configuration);

    @Test
    void inputQueueNotNull() {
        TextQueue inputQueue = connection.getInputQueue();
        assertThat(inputQueue, notNullValue());
    }

    @Test
    void outputQueueNotNull() {
        TextQueue outputQueue = connection.getOutputQueue();
        assertThat(outputQueue, notNullValue());
    }

    @Test
    void characterNotNull() {
        GameCharacter character = connection.getCharacter();
        assertThat(character, notNullValue());
    }

    @Test
    void optionMapEmptyByDefault() {
        Map<String, Long> menuOptionIds = connection.getMenuOptionIds();
        assertThat(menuOptionIds.size(), equalTo(0));
    }

    @Test
    void getLocaleNeverReturnsNull() {
        when(configuration.getServerLocale()).thenReturn("");
        assertThat(connection.getLocale(), notNullValue());
    }

    @Test
    void getLocaleReturnsPlayerLocale() {
        connection.setPlayer(new Player());
        String expected = RandomStringUtils.insecure().nextAlphabetic(5);
        connection.getPlayer().setLocale(expected);

        String locale = connection.getLocale();

        assertThat(locale, equalTo(expected));
    }

    @Test
    void getLocaleWithoutPlayerReturnsServerLocale() {
        connection.setPlayer(null);
        String expected = RandomStringUtils.insecure().nextAlphabetic(5);
        when(configuration.getServerLocale()).thenReturn(expected);

        String locale = connection.getLocale();

        assertThat(locale, equalTo(expected));
    }

    @Test
    void getLocaleWhenPlayerDoesNotHaveALocaleReturnsServerLocale() {
        Player player = new Player();
        player.setLocale("");
        connection.setPlayer(player);
        String expected = RandomStringUtils.insecure().nextAlphabetic(5);
        when(configuration.getServerLocale()).thenReturn(expected);

        String locale = connection.getLocale();

        assertThat(locale, equalTo(expected));
    }
}
