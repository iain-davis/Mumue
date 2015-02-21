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
public class UniverseRowProcessorTest {
    @Mock ResultSet resultSet;
    @Mock ComponentResultSetProcessor componentProcessor;
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
}
