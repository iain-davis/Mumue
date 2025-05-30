package org.mumue.mumue.connection.states;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.testobjectbuilder.Nimue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerConnectedTest {
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final CurrentTimestampProvider timestampProvider = mock(CurrentTimestampProvider.class);
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final PlayerRepository playerRepository = new PlayerRepository(database);
    private final Player player = Nimue.player().withId(RandomUtils.insecure().randomInt(1, 10000)).build();

    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final PlayerConnected playerConnected = new PlayerConnected(connectionStateProvider, timestampProvider, mock(PlayerMenuPrompt.class), mock(EnterUniverse.class), playerRepository);
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

        assertThat(returned, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    public void stateReturnsEnterUniverseWhenOnNoMenuConnection() {
        connection.getPortConfiguration().setSupportsMenus(false);

        ConnectionState returned = playerConnected.execute(connection, configuration);

        assertThat(returned, instanceOf(EnterUniverse.class));
    }

    @Test
    public void countPlayerUse() {
        connection.getPortConfiguration().setSupportsMenus(RandomUtils.insecure().randomBoolean());

        playerConnected.execute(connection, configuration);

        assertThat(player.getUseCount(), equalTo(1L));
    }

    @Test
    public void updateLastUsed() {
        connection.getPortConfiguration().setSupportsMenus(RandomUtils.insecure().randomBoolean());

        playerConnected.execute(connection, configuration);

        assertThat(player.getLastUsed(), equalTo(lastUsed));
    }

    @Test
    public void playerIsSaved() {
        connection.getPortConfiguration().setSupportsMenus(RandomUtils.insecure().randomBoolean());

        playerConnected.execute(connection, configuration);

        Player returned = playerRepository.get(player.getId());

        assertThat(returned.getUseCount(), equalTo(player.getUseCount()));
        assertThat(returned.getLastUsed().truncatedTo(ChronoUnit.SECONDS), equalTo(player.getLastUsed().truncatedTo(ChronoUnit.SECONDS)));
    }
}
