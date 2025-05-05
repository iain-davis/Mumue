package org.mumue.mumue.player;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.components.ComponentResultSetProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayerRowProcessorTest {
    private final ResultSet resultSet = mock(ResultSet.class);
    private final ComponentResultSetProcessor resultSetProcessor = mock(ComponentResultSetProcessor.class);
    private final PlayerRowProcessor processor = new PlayerRowProcessor(resultSetProcessor);

    @Test
    void toBeanReturnsPlayer() throws SQLException {
        Player player = processor.toBean(resultSet, Player.class);

        assertThat(player, instanceOf(Player.class));
    }

    @Test
    void toBeanReturnsPlayerWithLoginId() throws SQLException {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(17);
        when(resultSet.getString("loginId")).thenReturn(loginId);

        Player player = processor.toBean(resultSet, Player.class);

        assertThat(player.getLoginId(), equalTo(loginId));
    }

    @Test
    void toBeanReturnsPlayerWithLocale() throws SQLException {
        String locale = RandomStringUtils.insecure().nextAlphabetic(17);
        when(resultSet.getString("locale")).thenReturn(locale);

        Player player = processor.toBean(resultSet, Player.class);

        assertThat(player.getLocale(), equalTo(locale));
    }


    @Test
    void toBeanReturnsPlayerWithTrueAdministrator() throws SQLException {
        when(resultSet.getBoolean("administrator")).thenReturn(true);

        Player player = processor.toBean(resultSet, Player.class);

        assertThat(player.isAdministrator(), equalTo(true));
    }

    @Test
    void toBeanReturnsPlayerWithFalseAdministrator() throws SQLException {
        when(resultSet.getBoolean("administrator")).thenReturn(false);

        Player player = processor.toBean(resultSet, Player.class);

        assertThat(player.isAdministrator(), equalTo(false));
    }

    @Test
    void toBeanProcessesTimestamps() throws SQLException {
        processor.toBean(resultSet, Player.class);

        verify(resultSetProcessor).process(eq(resultSet), any(Player.class));
    }
}
