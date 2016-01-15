package org.mumue.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class NameableComponentResultSetProcessorTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    private final String name = RandomStringUtils.randomAlphabetic(17);
    private final String description = RandomStringUtils.randomAlphabetic(17);
    private final NameableComponent component = new NameableComponent() {
    };

    @Mock ResultSet resultSet;
    @Mock ComponentResultSetProcessor componentBaseProcessor;
    @InjectMocks NameableComponentResultSetProcessor processor;

    @Before
    public void beforeEach() throws SQLException {
        when(resultSet.getString("name")).thenReturn(name);
        when(resultSet.getString("description")).thenReturn(description);
    }

    @Test
    public void convertName() throws SQLException {
        processor.process(resultSet, component);

        assertThat(component.getName(), equalTo(name));
    }

    @Test
    public void convertDescription() throws SQLException {
        processor.process(resultSet, component);

        assertThat(component.getDescription(), equalTo(description));
    }

    @Test
    public void convertTimestamps() throws SQLException {
        processor.process(resultSet, component);

        verify(componentBaseProcessor).process(eq(resultSet), any(NameableComponent.class));
    }
}
