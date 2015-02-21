package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
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

@RunWith(MockitoJUnitRunner.class)
public class GameCharacterRowProcessorTest {
    @Mock ResultSet resultSet;
    @Mock LocatableComponentResultSetProcessor componentProcessor;
    @InjectMocks GameCharacterRowProcessor processor;

    @Test
    public void returnCharacter() throws SQLException {
        GameCharacter character = processor.toBean(resultSet, GameCharacter.class);
        assertNotNull(character);
    }

    @Test
    public void setPlayerId() throws SQLException {
        String playerId = RandomStringUtils.randomAlphabetic(17);
        when(resultSet.getString("playerId")).thenReturn(playerId);

        GameCharacter character = processor.toBean(resultSet, GameCharacter.class);

        assertThat(character.getPlayerId(), equalTo(playerId));
    }

    @Test
    public void useComponentResultSetProcessor() throws SQLException {
        processor.toBean(resultSet, GameCharacter.class);
        verify(componentProcessor).process(eq(resultSet), any(GameCharacter.class));
    }
}
