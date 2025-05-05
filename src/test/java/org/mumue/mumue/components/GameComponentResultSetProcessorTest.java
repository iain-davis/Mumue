package org.mumue.mumue.components;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameComponentResultSetProcessorTest {
    private final String name = RandomStringUtils.insecure().nextAlphabetic(17);
    private final String description = RandomStringUtils.insecure().nextAlphabetic(17);
    private final GameComponent component = new GameComponent() {
    };

    private final ResultSet resultSet = mock(ResultSet.class);
    private final ComponentResultSetProcessor componentBaseProcessor = mock(ComponentResultSetProcessor.class);
    private final GameComponentResultSetProcessor processor = new GameComponentResultSetProcessor(componentBaseProcessor);

    @BeforeEach
    void beforeEach() throws SQLException {
        when(resultSet.getString("name")).thenReturn(name);
        when(resultSet.getString("description")).thenReturn(description);
    }

    @Test
    void convertName() throws SQLException {
        processor.process(resultSet, component);

        assertThat(component.getName(), equalTo(name));
    }

    @Test
    void convertDescription() throws SQLException {
        processor.process(resultSet, component);

        assertThat(component.getDescription(), equalTo(description));
    }

    @Test
    void convertTimestamps() throws SQLException {
        processor.process(resultSet, component);

        verify(componentBaseProcessor).process(eq(resultSet), any(GameComponent.class));
    }
}
