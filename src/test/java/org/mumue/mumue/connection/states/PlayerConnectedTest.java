package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;

public class PlayerConnectedTest {
    private static final Random RANDOM = new Random();
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final CurrentTimestampProvider timestampProvider = mock(CurrentTimestampProvider.class);
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final PlayerRepository playerRepository = new PlayerRepository(database);
    private final Player player = TestObjectBuilder.player().withId(RANDOM.nextInt(10000)).build();

    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final PlayerConnected playerConnected = new PlayerConnected(timestampProvider, mock(DisplayPlayerMenu.class), mock(EnterUniverse.class), playerRepository);
    private final Instant lastUsed = Instant.now();

    @Before
    public void beforeEach() {
        database.update("delete from players");
        DatabaseHelper.insertPlayer(database, player.getId());
        when(timestampProvider.get()).thenReturn(lastUsed);
    }

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
    public void updateLastUsed() {
        connection.getPortConfiguration().setSupportsMenus(RANDOM.nextBoolean());

        playerConnected.execute(connection, configuration);

        assertThat(player.getLastUsed(), equalTo(lastUsed));
    }

    @Test
    public void playerIsSaved() {
        connection.getPortConfiguration().setSupportsMenus(RANDOM.nextBoolean());

        playerConnected.execute(connection, configuration);

        Player returned = playerRepository.get(player.getId());

        assertThat(returned.getUseCount(), equalTo(player.getUseCount()));
        assertThat(returned.getLastUsed(), equalTo(player.getLastUsed()));
    }
}