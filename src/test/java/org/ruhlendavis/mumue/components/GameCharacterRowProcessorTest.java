package org.ruhlendavis.mumue.components;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void returnUniverse() throws SQLException {
        GameCharacter character = processor.toBean(resultSet, GameCharacter.class);
        assertNotNull(character);
    }

    @Test
    public void useComponentResultSetProcessor() throws SQLException {
        processor.toBean(resultSet, GameCharacter.class);
        verify(componentProcessor).process(eq(resultSet), any(GameCharacter.class));
    }
}
