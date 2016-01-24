package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PlayerMenuDisplayTest {
    private final String menu = RandomStringUtils.randomAlphanumeric(17);
    private final String administratorMenu = RandomStringUtils.randomAlphanumeric(17);
    private final ApplicationConfiguration configuration = TestObjectBuilder.configuration();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Player player = TestObjectBuilder.player().build();
    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final PlayerMenuDisplay playerMenuDisplay = new PlayerMenuDisplay(mock(WaitForPlayerMenuChoice.class), textMaker);

    @Before
    public void beforeEach() {
        when(textMaker.getText(TextName.PlayerMainMenu, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(menu);
        when(textMaker.getText(TextName.AdministratorMainMenu, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(administratorMenu);
    }

    @Test
    public void executeReturnsNextStage() {
        ConnectionState next = playerMenuDisplay.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForPlayerMenuChoice.class));
    }

    @Test
    public void executePutsMainMenuOnOutputQueue() {
        playerMenuDisplay.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(menu));
    }

    @Test
    public void executeForAdministratorPlayerPutsAdministratorMainMenuOnOutputQueue() {
        player.setAdministrator(true);

        playerMenuDisplay.execute(connection, configuration);

        String expected = administratorMenu + menu;
        assertThat(connection.getOutputQueue(), contains(expected));
    }
}
