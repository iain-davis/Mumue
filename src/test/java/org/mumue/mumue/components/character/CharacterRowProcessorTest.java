package org.mumue.mumue.components.character;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.components.LocatableComponentResultSetProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CharacterRowProcessorTest {
    private final ResultSet resultSet = mock(ResultSet.class);
    private final LocatableComponentResultSetProcessor componentProcessor = mock(LocatableComponentResultSetProcessor.class);
    private final CharacterRowProcessor processor = new CharacterRowProcessor(componentProcessor);

    @Test
    void toBeanReturnsCharacter() throws SQLException {
        GameCharacter character = processor.toBean(resultSet, GameCharacter.class);
        assertThat(character, notNullValue());
    }

    @Test
    void toBeanSetsPlayerId() throws SQLException {
        long playerId = RandomUtils.insecure().randomLong(100, 200);
        when(resultSet.getLong("playerId")).thenReturn(playerId);

        GameCharacter character = processor.toBean(resultSet, GameCharacter.class);

        assertThat(character.getPlayerId(), equalTo(playerId));
    }

    @Test
    void toBeanUsesComponentResultSetProcessor() throws SQLException {
        processor.toBean(resultSet, GameCharacter.class);

        verify(componentProcessor).process(eq(resultSet), any(GameCharacter.class));
    }

    @Test
    void toBeanListNeverReturnsNull() throws SQLException {
        List<GameCharacter> characters = processor.toBeanList(resultSet, GameCharacter.class);

        assertThat(characters, notNullValue());
    }

    @Test
    void toBeanListReturnsCharacters() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);

        List<GameCharacter> characters = processor.toBeanList(resultSet, GameCharacter.class);

        assertThat(characters.size(), equalTo(2));
    }
}
