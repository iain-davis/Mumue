package org.mumue.mumue.player;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.mumue.mumue.components.ComponentResultSetProcessor;

@RunWith(MockitoJUnitRunner.class)
public class PlayerRowProcessorTest {
    @Mock ResultSet resultSet;
    @Mock ComponentResultSetProcessor resultSetProcessor;
    @InjectMocks PlayerRowProcessor processor;

    @Test
    public void toBeanReturnsPlayer() throws SQLException {
        Player player = processor.toBean(resultSet, Player.class);
        assertNotNull(player);
        assertThat(player, instanceOf(Player.class));
    }

    @Test
    public void toBeanReturnsPlayerWithLoginId() throws SQLException {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        when(resultSet.getString("loginId")).thenReturn(loginId);

        Player player = processor.toBean(resultSet, Player.class);

        assertThat(player.getLoginId(), equalTo(loginId));
    }

    @Test
    public void toBeanReturnsPlayerWithLocale() throws SQLException {
        String locale = RandomStringUtils.randomAlphabetic(17);
        when(resultSet.getString("locale")).thenReturn(locale);

        Player player = processor.toBean(resultSet, Player.class);

        assertThat(player.getLocale(), equalTo(locale));
    }


    @Test
    public void toBeanReturnsPlayerWithTrueAdministrator() throws SQLException {
        when(resultSet.getBoolean("administrator")).thenReturn(true);

        Player player = processor.toBean(resultSet, Player.class);

        assertTrue(player.isAdministrator());
    }

    @Test
    public void toBeanReturnsPlayerWithFalseAdministrator() throws SQLException {
        when(resultSet.getBoolean("administrator")).thenReturn(false);

        Player player = processor.toBean(resultSet, Player.class);

        assertFalse(player.isAdministrator());
    }

    @Test
    public void toBeanProcessesTimestamps() throws SQLException {
        processor.toBean(resultSet, Player.class);

        verify(resultSetProcessor).process(eq(resultSet), any(Player.class));
    }
}
