package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PlayerMenuPromptTest {
    private final String menu = RandomStringUtils.randomAlphanumeric(17);
    private final String administratorMenu = RandomStringUtils.randomAlphanumeric(17);
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Player player = Nimue.player().build();
    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final PlayerMenuPrompt playerMenuPrompt = new PlayerMenuPrompt(connectionStateProvider, textMaker);

    @Before
    public void beforeEach() {
        when(textMaker.getText(TextName.PlayerMainMenu, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(menu);
        when(textMaker.getText(TextName.AdministrationMenuOption, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(administratorMenu);
    }

    @Test
    public void executeReturnsNextStage() {
        ConnectionState next = playerMenuPrompt.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerMenuHandler.class));
    }

    @Test
    public void executePutsMainMenuOnOutputQueue() {
        playerMenuPrompt.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(menu));
    }

    @Test
    public void executeForAdministratorPlayerPutsAdministratorMainMenuOnOutputQueue() {
        player.setAdministrator(true);

        playerMenuPrompt.execute(connection, configuration);

        String expected = administratorMenu + menu;
        assertThat(connection.getOutputQueue(), contains(expected));
    }
}
