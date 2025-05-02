package org.mumue.mumue.components.character;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mumue.mumue.components.LocatableComponentResultSetProcessor;

@RunWith(MockitoJUnitRunner.class)
public class CharacterRowProcessorTest {
    @Mock ResultSet resultSet;
    @Mock LocatableComponentResultSetProcessor componentProcessor;
    @InjectMocks CharacterRowProcessor processor;

    @Test
    public void toBeanReturnsCharacter() throws SQLException {
        GameCharacter character = processor.toBean(resultSet, GameCharacter.class);
        assertNotNull(character);
    }

    @Test
    public void toBeanSetsPlayerId() throws SQLException {
        long playerId = RandomUtils.insecure().randomLong(100, 200);
        when(resultSet.getLong("playerId")).thenReturn(playerId);

        GameCharacter character = processor.toBean(resultSet, GameCharacter.class);

        assertThat(character.getPlayerId(), equalTo(playerId));
    }

    @Test
    public void toBeanUsesComponentResultSetProcessor() throws SQLException {
        processor.toBean(resultSet, GameCharacter.class);

        verify(componentProcessor).process(eq(resultSet), any(GameCharacter.class));
    }

    @Test
    public void toBeanListNeverReturnsNull() throws SQLException {
        List<GameCharacter> characters = processor.toBeanList(resultSet, GameCharacter.class);

        assertNotNull(characters);
    }

    @Test
    public void toBeanListReturnsCharacters() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);

        List<GameCharacter> characters = processor.toBeanList(resultSet, GameCharacter.class);

        assertThat(characters.size(), equalTo(2));
    }
}
