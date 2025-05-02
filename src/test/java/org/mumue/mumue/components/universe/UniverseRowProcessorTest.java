package org.mumue.mumue.components.universe;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mumue.mumue.components.GameComponentResultSetProcessor;

@RunWith(MockitoJUnitRunner.class)
public class UniverseRowProcessorTest {
    @Mock ResultSet resultSet;
    @Mock GameComponentResultSetProcessor componentProcessor;
    @InjectMocks UniverseRowProcessor processor;

    @Test
    public void useComponentResultSetProcessor() throws SQLException {
        processor.toBean(resultSet, Universe.class);
        verify(componentProcessor).process(eq(resultSet), any(Universe.class));
    }

    @Test
    public void returnUniverse() throws SQLException {
        Universe universe = processor.toBean(resultSet, Universe.class);
        assertNotNull(universe);
    }

    @Test
    public void setStartingSpaceId() throws SQLException {
        long id = RandomUtils.insecure().randomLong(200, 300);
        when(resultSet.getLong("startingSpaceId")).thenReturn(id);

        Universe universe = processor.toBean(resultSet, Universe.class);

        assertThat(universe.getStartingSpaceId(), equalTo(id));
    }
}
