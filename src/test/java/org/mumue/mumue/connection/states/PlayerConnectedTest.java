package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Random;

import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.connection.states.mainmenu.DisplayPlayerMenu;
import org.mumue.mumue.player.Player;

public class PlayerConnectedTest {
    private static final Random RANDOM = new Random();
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final CurrentTimestampProvider timestampProvider = mock(CurrentTimestampProvider.class);
    private final Player player = new Player();

    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final PlayerConnected playerConnected = new PlayerConnected(timestampProvider, mock(DisplayPlayerMenu.class), mock(EnterUniverse.class));

    @Test
    public void stateReturnsMenuPrompt() {
        connection.getPortConfiguration().setSupportsMenus(true);

        ConnectionState returned = playerConnected.execute(connection, configuration);

        assertThat(returned, instanceOf(DisplayPlayerMenu.class));
    }

    @Test
    public void stateReturnsEnterUniverseWhenOnNoMenuConnection() {
        connection.getPortConfiguration().setSupportsMenus(false);

        ConnectionState returned = playerConnected.execute(connection, configuration);

        assertThat(returned, instanceOf(EnterUniverse.class));
    }

    @Test
    public void countPlayerUse() {
        connection.getPortConfiguration().setSupportsMenus(RANDOM.nextBoolean());

        playerConnected.execute(connection, configuration);

        assertThat(player.getUseCount(), equalTo(1L));
    }

    @Test
    public void updateLastModified() {
        connection.getPortConfiguration().setSupportsMenus(RANDOM.nextBoolean());
        Instant lastModified = Instant.now();
        when(timestampProvider.get()).thenReturn(lastModified);

        playerConnected.execute(connection, configuration);

        assertThat(player.getLastModified(), equalTo(lastModified));
    }
}