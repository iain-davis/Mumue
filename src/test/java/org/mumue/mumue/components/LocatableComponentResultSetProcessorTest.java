package org.mumue.mumue.components;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LocatableComponentResultSetProcessorTest {
    private final Long locationId = RandomUtils.insecure().randomLong(100, 200);
    private final Long universeId = RandomUtils.insecure().randomLong(100, 200);
    private final LocatableComponent component = new LocatableComponent() {};

    private final ResultSet resultSet = mock(ResultSet.class);
    private final GameComponentResultSetProcessor componentResultSetProcessor = mock(GameComponentResultSetProcessor.class);
    private final LocatableComponentResultSetProcessor processor = new LocatableComponentResultSetProcessor(componentResultSetProcessor);

    @BeforeEach
    void beforeEach() throws SQLException {
        when(resultSet.getLong("locationId")).thenReturn(locationId);
        when(resultSet.getLong("universeId")).thenReturn(universeId);
    }

    @Test
    void convertLocationId() throws SQLException {
        processor.process(resultSet, component);

        assertThat(component.getLocationId(), equalTo(locationId));
    }

    @Test
    void convertUniverseId() throws SQLException {
        processor.process(resultSet, component);

        assertThat(component.getUniverseId(), equalTo(universeId));
    }

    @Test
    void convertComponentFields() throws SQLException {
        processor.process(resultSet, component);

        verify(componentResultSetProcessor).process(resultSet, component);
    }
}
