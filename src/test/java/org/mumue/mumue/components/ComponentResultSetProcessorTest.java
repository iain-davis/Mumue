package org.mumue.mumue.components;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ComponentResultSetProcessorTest {
    private final Instant instant = Instant.EPOCH;
    private final long useCount = RandomUtils.insecure().randomLong(100, 200);
    private final long id = RandomUtils.insecure().randomLong(200, 1000);

    private final ResultSet resultSet = mock(ResultSet.class);
    private final Component timestampAble = new Component() {};
    private final ComponentResultSetProcessor processor = new ComponentResultSetProcessor() {};

    @BeforeEach
    void beforeEach() throws SQLException {
        Timestamp timestamp = Timestamp.from(instant);
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getTimestamp("created")).thenReturn(timestamp);
        when(resultSet.getTimestamp("lastUsed")).thenReturn(timestamp);
        when(resultSet.getLong("useCount")).thenReturn(useCount);
    }

    @Test
    void convertId() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getId(), equalTo(id));
    }

    @Test
    void convertCreated() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getCreated(), equalTo(instant));
    }

    @Test
    void convertLastModified() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getLastModified(), equalTo(instant));
    }

    @Test
    void convertLastUsed() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getLastUsed(), equalTo(instant));
    }

    @Test
    void convertUseCount() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getUseCount(), equalTo(useCount));
    }
}
