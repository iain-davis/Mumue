package org.mumue.mumue.database;

import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mumue.mumue.database.MyVarargMatcher.myVarargMatcher;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DatabaseAccessorTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    private final QueryRunner queryRunner = mock(QueryRunner.class);
    private final DatabaseAccessor database = new DatabaseAccessor(queryRunner);

    private final String sql = RandomStringUtils.randomAlphabetic(17);
    private final ResultSetHandler rsh = new ResultHandler();
    private final Object foo = new Object();

    @Test
    public void queryUsesQueryRunner() throws SQLException {
        database.query(sql, rsh, foo);
        verify(queryRunner).query(sql, rsh, foo);
    }

    @Test
    public void queryHandlesException() throws SQLException {
        when(queryRunner.query(sql, rsh, foo)).thenThrow(new SQLException());

        thrown.expect(RuntimeException.class);

        database.query(sql, rsh, foo);
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
        database.update(sql, foo);
        verify(queryRunner).update(eq(sql), argThat(myVarargMatcher(foo)));
    }

    @Test
    public void updateWithParameterHandlesException() throws SQLException {
        when(queryRunner.update(eq(sql), argThat(myVarargMatcher(foo)))).thenThrow(new SQLException());

        thrown.expect(RuntimeException.class);

        database.update(sql, foo);
    }

    private class ResultHandler implements ResultSetHandler {
        @Override
        public Object handle(ResultSet rs) throws SQLException {
            return null;
        }
    }
}

