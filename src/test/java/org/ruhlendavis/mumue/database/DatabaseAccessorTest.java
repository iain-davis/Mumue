package org.ruhlendavis.mumue.database;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class DatabaseAccessorTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Rule public ExpectedException thrown = ExpectedException.none();
    @Mock QueryRunner queryRunner;
    @InjectMocks DatabaseAccessor database;

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
        when(queryRunner.query(sql, rsh, foo)).thenThrow(SQLException.class);

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
        when(queryRunner.update(sql)).thenThrow(SQLException.class);

        thrown.expect(RuntimeException.class);

        database.update(sql);
    }

    private class ResultHandler implements ResultSetHandler {
        @Override
        public Object handle(ResultSet rs) throws SQLException {
            return null;
        }
    }
}
