package org.mumue.mumue.components.space;

import org.junit.jupiter.api.Test;
import org.mumue.mumue.components.LocatableComponentResultSetProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SpaceRowProcessorTest {
    private final ResultSet resultSet = mock(ResultSet.class);
    private final LocatableComponentResultSetProcessor locatableComponentResultSetProcessor = mock(LocatableComponentResultSetProcessor.class);
    private final SpaceRowProcessor processor = new SpaceRowProcessor(locatableComponentResultSetProcessor);

    @Test
    void useComponentResultSetProcessor() throws SQLException {
        processor.toBean(resultSet, Space.class);
        verify(locatableComponentResultSetProcessor).process(eq(resultSet), any(Space.class));
    }

    @Test
    void returnSpace() throws SQLException {
        Space space = processor.toBean(resultSet, Space.class);
        assertThat(space, notNullValue());
    }
}
