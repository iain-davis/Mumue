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
public class SpaceRowProcessorTest {
    @Mock ResultSet resultSet;
    @Mock LocatableComponentResultSetProcessor locatableComponentResultSetProcessor;
    @InjectMocks SpaceRowProcessor processor;

    @Test
    public void useComponentResultSetProcessor() throws SQLException {
        processor.toBean(resultSet, Space.class);
        verify(locatableComponentResultSetProcessor).process(eq(resultSet), any(Space.class));
    }

    @Test
    public void returnSpace() throws SQLException {
        Space space = processor.toBean(resultSet, Space.class);
        assertNotNull(space);
    }
}
