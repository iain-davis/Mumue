package org.mumue.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.mumue.mumue.player.Player;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class DisplayPlayerMenuTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks DisplayPlayerMenu stage;

    private final String menu = RandomStringUtils.randomAlphanumeric(17);
    private final String administratorMenu = RandomStringUtils.randomAlphanumeric(17);
    private final String locale = RandomStringUtils.randomAlphabetic(15);
    private final Player player = new PlayerBuilder().withLocale(locale).build();
    private final Connection connection = new Connection(configuration).withPlayer(player);

    @Before
    public void beforeEach() {
        when(textMaker.getText(eq(TextName.PlayerMainMenu), eq(locale))).thenReturn(menu);
        when(textMaker.getText(eq(TextName.AdministratorMainMenu), eq(locale))).thenReturn(administratorMenu);
    }

    @Test
    public void executeReturnsNextStage() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForPlayerMenuChoice.class));
    }

    @Test
    public void executePutsMainMenuOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(menu));
    }

    @Test
    public void executeForAdministratorPlayerPutsAdministratorMainMenuOnOutputQueue() {
        player.setAdministrator(true);

        stage.execute(connection, configuration);

        String expected = administratorMenu + menu;
        assertThat(connection.getOutputQueue(), contains(expected));
    }
}
