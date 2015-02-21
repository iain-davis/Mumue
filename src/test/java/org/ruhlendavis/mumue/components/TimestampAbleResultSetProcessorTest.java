package org.ruhlendavis.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimestampAbleResultSetProcessorTest {
    private final Instant instant = Instant.EPOCH;
    private final long useCount = RandomUtils.nextLong(100, 200);

    @Mock ResultSet resultSet;
    private final TimestampAble timestampAble = new TimestampAble() {};
    private final TimestampAbleResultSetProcessor processor = new TimestampAbleResultSetProcessor() {};

    @Before
    public void beforeEach() throws SQLException {
        Timestamp timestamp = Timestamp.from(instant);
        when(resultSet.getTimestamp("created")).thenReturn(timestamp);
        when(resultSet.getTimestamp("lastModified")).thenReturn(timestamp);
        when(resultSet.getTimestamp("lastUsed")).thenReturn(timestamp);
        when(resultSet.getLong("useCount")).thenReturn(useCount);
    }

    @Test
    public void convertCreated() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getCreated(), equalTo(instant));
    }

    @Test
    public void convertLastModified() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getLastModified(), equalTo(instant));
    }

    @Test
    public void convertLastUsed() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getLastUsed(), equalTo(instant));
    }

    @Test
    public void convertUseCount() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getUseCount(), equalTo(useCount));
    }
}