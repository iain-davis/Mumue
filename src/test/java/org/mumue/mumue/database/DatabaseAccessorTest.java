package org.mumue.mumue.database;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DatabaseAccessorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final QueryRunner queryRunner = mock(QueryRunner.class);
    private final DatabaseAccessor database = new DatabaseAccessor(queryRunner);

    private final String sql = RandomStringUtils.insecure().nextAlphabetic(17);
    private final ResultSetHandler<Object> resultHandler = new FakeResultHandler();
    private final Object sampleArgument = new Object();
    private final Object[] sampleArguments = new Object[1];

    @Before
    public void beforeEach() {
    }

    @Test
    public void queryUsesQueryRunner() throws SQLException {
        database.query(sql, resultHandler, sampleArgument);
        verify(queryRunner).query(sql, resultHandler, sampleArgument);
    }

    @Test
    public void queryHandlesException() throws SQLException {
        when(queryRunner.query(sql, resultHandler, sampleArgument)).thenThrow(new SQLException());

        thrown.expect(RuntimeException.class);

        database.query(sql, resultHandler, sampleArgument);
    }

    @Test
    public void updateUsesQueryRunner() throws SQLException {
        database.update(sql);
        verify(queryRunner).update(sql);
    }

    @Test
    public void updateHandlesException() throws SQLException {
        when(queryRunner.update(sql)).thenThrow(new SQLException());

        thrown.expect(RuntimeException.class);

        database.update(sql);
    }

    @Test
    public void updateWithParameterUsesQueryRunner() throws SQLException {
        sampleArguments[0] = sampleArgument;

        database.update(sql, sampleArgument);

        verify(queryRunner).update(eq(sql), eq(sampleArguments));
    }

    @Test
    public void updateWithParameterHandlesException() throws SQLException {
        sampleArguments[0] = sampleArgument;
        when(queryRunner.update(eq(sql), eq(sampleArguments))).thenThrow(new SQLException());

        thrown.expect(RuntimeException.class);

        database.update(sql, sampleArgument);
    }

    private static class FakeResultHandler implements ResultSetHandler<Object> {
        @Override
        public Object handle(ResultSet resultSet) {
            return null;
        }
    }
}

