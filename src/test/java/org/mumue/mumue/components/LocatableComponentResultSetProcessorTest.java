package org.mumue.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocatableComponentResultSetProcessorTest {
    private final Long locationId = RandomUtils.nextLong(100, 200);
    private final Long universeId = RandomUtils.nextLong(100, 200);
    private final LocatableComponent component = new LocatableComponent() {};

    @Mock ResultSet resultSet;
    @Mock ComponentResultSetProcessor componentResultSetProcessor;
    @InjectMocks LocatableComponentResultSetProcessor processor;

    @Before
    public void beforeEach() throws SQLException {
        when(resultSet.getLong("locationId")).thenReturn(locationId);
        when(resultSet.getLong("universeId")).thenReturn(universeId);
    }

    @Test
    public void convertLocationId() throws SQLException {
        processor.process(resultSet, component);

        assertThat(component.getLocationId(), equalTo(locationId));
    }

    @Test
    public void convertUniverseId() throws SQLException {
        processor.process(resultSet, component);

        assertThat(component.getUniverseId(), equalTo(universeId));
    }

    @Test
    public void convertComponentFields() throws SQLException {
        processor.process(resultSet, component);

        verify(componentResultSetProcessor).process(resultSet, component);
    }
}
