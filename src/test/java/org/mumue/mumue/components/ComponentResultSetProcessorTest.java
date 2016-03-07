package org.mumue.mumue.components;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mumue.mumue.testobjectbuilder.Nimue;

public class ComponentResultSetProcessorTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    private final Instant created = Nimue.randomInstant();
    private final Instant lastModified = Nimue.randomInstant();
    private final Instant lastUsed = Nimue.randomInstant();
    private final Instant instant = Instant.EPOCH;
    private final long useCount = RandomUtils.insecure().randomLong(100, 200);
    private final long id = RandomUtils.insecure().randomLong(200, 1000);

    @Mock ResultSet resultSet;
    private final Component timestampAble = new Component() {};
    private final ComponentResultSetProcessor processor = new ComponentResultSetProcessor() {};

    @Before
    public void beforeEach() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getTimestamp("created")).thenReturn(Timestamp.from(created));
        when(resultSet.getTimestamp("lastModified")).thenReturn(Timestamp.from(lastModified));
        when(resultSet.getTimestamp("lastUsed")).thenReturn(Timestamp.from(lastUsed));
        when(resultSet.getLong("useCount")).thenReturn(useCount);
    }

    @Test
    public void convertId() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getId(), equalTo(id));
    }

    @Test
    public void convertCreated() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getCreated(), equalTo(created));
    }

    @Test
    public void convertLastModified() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getLastModified(), equalTo(lastModified));
    }

    @Test
    public void convertLastUsed() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getLastUsed(), equalTo(lastUsed));
    }

    @Test
    public void convertUseCount() throws SQLException {
        processor.process(resultSet, timestampAble);

        assertThat(timestampAble.getUseCount(), equalTo(useCount));
    }
}
