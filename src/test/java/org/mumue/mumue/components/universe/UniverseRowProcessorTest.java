package org.mumue.mumue.components.universe;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.components.GameComponentResultSetProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UniverseRowProcessorTest {
    private final ResultSet resultSet = mock(ResultSet.class);
    private final GameComponentResultSetProcessor componentProcessor = mock(GameComponentResultSetProcessor.class);
    private final UniverseRowProcessor processor = new UniverseRowProcessor(componentProcessor);

    @Test
    void useComponentResultSetProcessor() throws SQLException {
        processor.toBean(resultSet, Universe.class);
        verify(componentProcessor).process(eq(resultSet), any(Universe.class));
    }

    @Test
    void returnUniverse() throws SQLException {
        Universe universe = processor.toBean(resultSet, Universe.class);
        assertThat(universe, notNullValue());
    }

    @Test
    void setStartingSpaceId() throws SQLException {
        long id = RandomUtils.insecure().randomLong(200, 300);
        when(resultSet.getLong("startingSpaceId")).thenReturn(id);

        Universe universe = processor.toBean(resultSet, Universe.class);

        assertThat(universe.getStartingSpaceId(), equalTo(id));
    }
}
